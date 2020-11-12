package ar.edu.iua.business;

import ar.edu.iua.business.exception.*;
import ar.edu.iua.model.*;
import ar.edu.iua.model.persistence.OrdenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenBusiness implements IOrdenBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrdenRepository ordenDAO;
    @Autowired
    private OrdenDetalleBusiness ordenDetalleBusiness;

    @Override
    public Orden load(Long id) throws BusinessException, NotFoundException {
        Optional<Orden> op;
        try {
            op = ordenDAO.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra el orden id=" + id);
        return op.get();
    }

    @Override
    public List<Orden> list() throws BusinessException {
        try {
            return ordenDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Orden save(Orden orden) throws BusinessException {

        try {
            orden.setEstado(1);
            orden.setCaudal(0);
            orden.setDensidad(0);
            Date fechaGen = java.util.Calendar.getInstance().getTime();
            orden.setFechaGeneracionOrden(fechaGen);
            orden.setFechaUltimoAlmacenamiento(null);
            orden.setMasaAcumulada(0);
            orden.setNumeroOrden(generarNumeroOrden());
            orden.setTemperatura(0);
            orden.setPassword("");
            orden.setPesajeInicial(0);
            orden.setFechaPesaje(null);
            return ordenDAO.save(orden);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void delete(Long id) throws BusinessException, NotFoundException {
        try {
            ordenDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e1) {
            throw new NotFoundException("No se encuentra el orden id=" + id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Orden actualizarSurtidor(OrdenSurtidorDTO ordenSurtidorDTO) throws BusinessException,
            NotFoundException, InvalidStateOrderException, InvalidPasswordOrderException,
            FullTankException, PresetLimitException {
        Orden orden = null;
        try {
            String numeroOrden = getNumeroOrden(ordenSurtidorDTO.getIdOrden());
            orden = findByNumeroOrden(numeroOrden);
            if (!orden.getPassword().equals(ordenSurtidorDTO.getPassword())) {
                throw new InvalidPasswordOrderException("Password Inválido");
            }

            if (orden.getEstado() != 2) {
                throw new InvalidStateOrderException("La orden no se encuentra en estado 2.");
            }

            double capacidad = 0;

            for (Cisterna c : orden.getCamion().getCisternaList()) {
                capacidad += c.getCapacidad();
            }

            if (ordenSurtidorDTO.getMasaAcumulada() > capacidad) {
                throw new FullTankException("No se puede cargar mas combustible, se excede la capacidad del camion");
            }

            if (ordenSurtidorDTO.getMasaAcumulada() > orden.getPreset()) {
                throw new PresetLimitException("No se puede cargar mas combustible, se excede el preset");
            }

            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dateSurtidor = inputDF.parse(ordenSurtidorDTO.getFecha());

            double caudal = (ordenSurtidorDTO.getMasaAcumulada() - orden.getMasaAcumulada()) / 1;

            double densidad = ordenSurtidorDTO.getMasaAcumulada() / capacidad;

            OrdenDetalle ordenDetalle = new OrdenDetalle(ordenSurtidorDTO.getMasaAcumulada(), densidad, ordenSurtidorDTO.getTemperatura(), caudal, orden.getId());

            if (orden.getFechaUltimoAlmacenamiento() != null) {
                System.out.println(orden.getFechaUltimoAlmacenamiento());

                if ((dateSurtidor.getTime() - orden.getFechaUltimoAlmacenamiento().getTime()) >= 10000) {
                    ordenDetalleBusiness.save(ordenDetalle);
                    ordenDAO.actualizarOrdenSurtidorConFecha(orden.getId(), caudal, densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada(), dateSurtidor);
                } else {
                    ordenDAO.actualizarOrdenSurtidor(orden.getId(), caudal, densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada());
                }
            } else {
                ordenDetalleBusiness.save(ordenDetalle);
                ordenDAO.actualizarOrdenSurtidorConFecha(orden.getId(), caudal, densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada(), dateSurtidor);
            }
        } catch (InvalidStateOrderException e) {
            log.error(e.getMessage(), e);
            throw new InvalidStateOrderException("La orden no se encuentra en estado 2.");
        } catch (InvalidPasswordOrderException e) {
            log.error(e.getMessage(), e);
            throw new InvalidPasswordOrderException("Password Inválido");
        } catch (FullTankException e) {
            log.error(e.getMessage(), e);
            throw new FullTankException("No se puede cargar mas combustible, se excede la capacidad del camion");
        } catch (PresetLimitException e) {
            log.error(e.getMessage(), e);
            throw new PresetLimitException("No se puede cargar mas combustible, se excede el preset");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (orden == null) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return orden;
    }

    public Orden findByNumeroOrden(String orden) throws BusinessException, NotFoundException {
        Orden order = null;
        try {
            order = ordenDAO.findByNumeroOrden(orden);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (order == null) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return order;
    }

    private String generarNumeroOrden() {
        String idUltimaOrdenSt = ordenDAO.getUltimoIdOrden();
        if (idUltimaOrdenSt == null) {
            return "000001";
        }
        int idUltimaOrden = Integer.parseInt(idUltimaOrdenSt);
        int nuevoNumeroOrden = idUltimaOrden + 1;
        String numeroOrden = "";
        if (nuevoNumeroOrden <= 9) {
            numeroOrden = "00000" + nuevoNumeroOrden;
        } else if (nuevoNumeroOrden > 9 && nuevoNumeroOrden < 99) {
            numeroOrden = "0000" + nuevoNumeroOrden;
        } else if (nuevoNumeroOrden > 99 && nuevoNumeroOrden < 999) {
            numeroOrden = "000" + nuevoNumeroOrden;
        } else if (nuevoNumeroOrden > 999 && nuevoNumeroOrden < 9999) {
            numeroOrden = "00" + nuevoNumeroOrden;
        } else if (nuevoNumeroOrden > 9999 && nuevoNumeroOrden < 99999) {
            numeroOrden = "0" + nuevoNumeroOrden;
        } else {
            numeroOrden = "" + nuevoNumeroOrden;
        }
        return numeroOrden;
    }

    private String getNumeroOrden(String orden) {
        String ordenNueva = "";
        if (orden.length() == 1) {
            ordenNueva = "00000" + orden;
        } else if (orden.length() == 2) {
            ordenNueva = "0000" + orden;
        } else if (orden.length() == 3) {
            ordenNueva = "000" + orden;
        } else if (orden.length() == 4) {
            ordenNueva = "00" + orden;
        } else if (orden.length() == 5) {
            ordenNueva = "0" + orden;
        } else if (orden.length() >= 6) {
            ordenNueva = "" + orden;
        }
        return ordenNueva;
    }

    @Override
    public Orden actualizarPesajeInicial(PesajeDTO pesajeDTO) throws BusinessException, NotFoundException, InvalidStateOrderException {
        Orden orden = null;
        try {
            String numeroOrden = getNumeroOrden(pesajeDTO.getIdOrden());
            orden = findByNumeroOrden(numeroOrden);

            if (orden.getEstado() != 1) {
                throw new InvalidStateOrderException("La orden no se encuentra en estado 1.");
            }

            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dateSurtidor = inputDF.parse(pesajeDTO.getFechaPesaje());
            String password = generarRandomPassword(5);
            System.out.println("===============================================");
            System.out.println(password);
            System.out.println("===============================================");
            ordenDAO.actualizarPesajeInicial(numeroOrden, pesajeDTO.getPeso(), dateSurtidor, 2, password);
            orden = load(orden.getId());
        } catch (BusinessException | ParseException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        } catch (InvalidStateOrderException e) {
            log.error(e.getMessage(), e);
            throw new InvalidStateOrderException("La orden no se encuentra en estado 1.");
        }
        if (orden == null) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return orden;
    }

    public static String generarRandomPassword(int len) {
        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(randomIndex));
        }
        return sb.toString();
    }
}
