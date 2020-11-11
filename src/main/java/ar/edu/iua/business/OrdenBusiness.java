package ar.edu.iua.business;

import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.*;
import ar.edu.iua.model.persistence.OrdenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.DateFormat;
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
            sssss(fechaGen);
            orden.setFechaGeneracionOrden(fechaGen);
            orden.setFechaUltimoAlmacenamiento(null);
            orden.setMasaAcumulada(0);
            orden.setNumeroOrden(generarNumeroOrden());
            orden.setTemperatura(0);
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
    public Orden actualizarSurtidor(OrdenSurtidorDTO ordenSurtidorDTO) throws BusinessException, NotFoundException, IllegalStateException {
        Orden orden = null;
        try {
            orden = load(ordenSurtidorDTO.getIdOrden());

            if(orden.getEstado() != 2){
                throw new IllegalStateException("La orden no se encuentra en estado 2.");
            }

            double capacidad = 0;

            for(Cisterna c : orden.getCamion().getCisternaList()){
                capacidad += c.getCapacidad();
            }

            if(ordenSurtidorDTO.getMasaAcumulada() > capacidad || ordenSurtidorDTO.getMasaAcumulada() > orden.getPreset()){
                throw new IllegalStateException("No se puede cargar mas combustible, se excede la capacidad o el preset");
            }

            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dateSurtidor = inputDF.parse(ordenSurtidorDTO.getFecha());

            double caudal = (ordenSurtidorDTO.getMasaAcumulada() - orden.getMasaAcumulada())/1;

            double densidad = ordenSurtidorDTO.getMasaAcumulada() / capacidad;

            OrdenDetalle ordenDetalle = new OrdenDetalle(ordenSurtidorDTO.getMasaAcumulada(), densidad, ordenSurtidorDTO.getTemperatura(),caudal, orden.getId());

            if(orden.getFechaUltimoAlmacenamiento() != null){
                System.out.println(orden.getFechaUltimoAlmacenamiento());

                if ((dateSurtidor.getTime() - orden.getFechaUltimoAlmacenamiento().getTime()) >= 10000) {
                    ordenDetalleBusiness.save(ordenDetalle);
                    ordenDAO.actualizarOrdenSurtidorConFecha(orden.getId(), caudal,densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada(), dateSurtidor);
                }else{
                    ordenDAO.actualizarOrdenSurtidor(orden.getId(), caudal,densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada());
                }
            }else{
                ordenDetalleBusiness.save(ordenDetalle);
                ordenDAO.actualizarOrdenSurtidorConFecha(orden.getId(), caudal,densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada(), dateSurtidor);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (orden == null) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return orden;
    }

    private String generarNumeroOrden(){
        String idUltimaOrdenSt = ordenDAO.getUltimoIdOrden();
        System.out.println("-------------------------");
        System.out.println("orden: " + idUltimaOrdenSt);
        System.out.println("-------------------------");
        if(idUltimaOrdenSt == null){
            return "000001";
        }
        int idUltimaOrden = Integer.parseInt(idUltimaOrdenSt);
        int nuevoNumeroOrden = idUltimaOrden+1;
        String numeroOrden = "";
        if(nuevoNumeroOrden <= 9){
            numeroOrden = "00000" + nuevoNumeroOrden;
        } else if(nuevoNumeroOrden > 9 && nuevoNumeroOrden < 99){
            numeroOrden = "0000" + nuevoNumeroOrden;
        } else if(nuevoNumeroOrden > 99 && nuevoNumeroOrden < 999){
            numeroOrden = "000" + nuevoNumeroOrden;
        } else if(nuevoNumeroOrden > 999 && nuevoNumeroOrden < 9999){
            numeroOrden = "00" + nuevoNumeroOrden;
        } else if(nuevoNumeroOrden > 9999 && nuevoNumeroOrden < 99999){
            numeroOrden = "0" + nuevoNumeroOrden;
        } else {
            numeroOrden = "" + nuevoNumeroOrden;
        }
        return numeroOrden;
    }

    public void sssss(Date d){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(d.toString());
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }
}
