package ar.edu.iua.business;

import ar.edu.iua.business.exception.*;
import ar.edu.iua.model.Orden;
import ar.edu.iua.model.OrdenSurtidorDTO;
import ar.edu.iua.model.PesajeDTO;

import java.util.List;

public interface IOrdenBusiness {

    public Orden load(Long id) throws BusinessException, NotFoundException;

    public List<Orden> list() throws BusinessException;

    public Orden save(Orden orden) throws BusinessException;

    public void delete(Long id) throws BusinessException, NotFoundException;

    public Orden actualizarSurtidor(OrdenSurtidorDTO ordenSurtidorDTO) throws BusinessException,
            NotFoundException, InvalidStateOrderException, InvalidPasswordOrderException,
            FullTankException, PresetLimitException;

    public Orden findByNumeroOrden(String orden) throws BusinessException, NotFoundException;

    public Orden actualizarPesajeInicial(PesajeDTO pesajeDTO) throws BusinessException, NotFoundException,
            InvalidStateOrderException;
}
