package ar.edu.iua.business;

import ar.edu.iua.business.exception.*;
import ar.edu.iua.model.Conciliacion;

import java.util.List;

public interface IConciliacionBusiness {

    public Conciliacion load(Long id) throws BusinessException, NotFoundException;

    public List<Conciliacion> list() throws BusinessException;

    public Conciliacion save(Conciliacion orden) throws BusinessException;

    public void delete(Long id) throws BusinessException, NotFoundException;
}
