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
    public Orden actualizarSurtidor(OrdenSurtidorDTO ordenSurtidorDTO) throws BusinessException, NotFoundException {
        Orden orden = null;
        try {
            orden = load(ordenSurtidorDTO.getIdOrden());
            double capacidad = 0;

            for(Cisterna c : orden.getCamion().getCisternaList()){
                capacidad += c.getCapacidad();
            }

            if(ordenSurtidorDTO.getMasaAcumulada() > capacidad){
                return null;
            }

            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date dateSurtidor = inputDF.parse(ordenSurtidorDTO.getFecha());

            double caudal = (ordenSurtidorDTO.getMasaAcumulada() - orden.getMasaAcumulada())/1;

            double densidad = ordenSurtidorDTO.getMasaAcumulada() / capacidad;

            OrdenDetalle ordenDetalle = new OrdenDetalle(ordenSurtidorDTO.getMasaAcumulada(), densidad, ordenSurtidorDTO.getTemperatura(),caudal, orden.getId());

            if(orden.getFechaUltimoAlmacenamiento() != null){
                System.out.println(orden.getFechaUltimoAlmacenamiento());

                if ((dateSurtidor.getTime() - orden.getFechaUltimoAlmacenamiento().getTime()) >= 10000) {
                    //ordenDetalleBusiness.guardar(ordenDetalle);
                    //ordenDetalleBusiness.save(ordenDetalle);
                    ordenDAO.actualizarOrdenSurtidorConFecha(orden.getId(), caudal,densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada(), dateSurtidor);
                }else{
                    ordenDAO.actualizarOrdenSurtidor(orden.getId(), caudal,densidad, ordenSurtidorDTO.getTemperatura(), ordenSurtidorDTO.getMasaAcumulada());
                }
            }else{
                //ordenDetalleBusiness.guardar(ordenDetalle);
                //ordenDetalleBusiness.save(ordenDetalle);
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
}
