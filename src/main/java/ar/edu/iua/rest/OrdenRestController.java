package ar.edu.iua.rest;

import ar.edu.iua.business.IOrdenBusiness;
import ar.edu.iua.business.exception.*;
import ar.edu.iua.model.Orden;
import ar.edu.iua.model.OrdenSurtidorDTO;
import ar.edu.iua.model.PesajeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constantes.URL_ORDENES)
public class OrdenRestController extends BaseRestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IOrdenBusiness ordenBusiness;

    @GetMapping(value = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Orden>> list() {
        try {
            return new ResponseEntity<List<Orden>>(ordenBusiness.list(), HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<List<Orden>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Orden orden) {
        try {
            ordenBusiness.save(orden);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("location", Constantes.URL_ORDENES + "/" + orden.getId());
            return new ResponseEntity<String>(responseHeaders, HttpStatus.CREATED);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = {""}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Orden orden) {
        try {
            ordenBusiness.save(orden);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orden> load(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<Orden>(ordenBusiness.load(id), HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<Orden>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Orden>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            ordenBusiness.delete(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "surtidor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orden> actualizarSurtidor(@RequestBody OrdenSurtidorDTO ordenSurtidorDTO) {
        Orden p = null;
        try {
            p = ordenBusiness.actualizarSurtidor(ordenSurtidorDTO);
            return new ResponseEntity<Orden>(p, HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<Orden>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Orden>(HttpStatus.NOT_FOUND);
        } catch (InvalidPasswordOrderException | PresetLimitException | FullTankException |
                OutOfDateException | InvalidStateOrderException e) {
            return new ResponseEntity<Orden>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "pesaje", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orden> actualizarPesajeInicial(@RequestBody PesajeDTO pesajeDTO) {
        Orden p = null;
        try {
            p = ordenBusiness.actualizarPesajeInicial(pesajeDTO);
            return new ResponseEntity<Orden>(p, HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<Orden>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Orden>(HttpStatus.NOT_FOUND);
        } catch (InvalidStateOrderException | OutOfDateException e) {
            return new ResponseEntity<Orden>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "cerrarOrden", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orden> cerrarOrden(@RequestBody String numeroOrden) {
        Orden p = null;
        try {
            p = ordenBusiness.cerrarOrdenPorNumeroOrden(numeroOrden);
            return new ResponseEntity<Orden>(p, HttpStatus.OK);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<Orden>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NotFoundException e) {
            return new ResponseEntity<Orden>(HttpStatus.NOT_FOUND);
        } catch (InvalidStateOrderException e) {
            return new ResponseEntity<Orden>(HttpStatus.BAD_REQUEST);
        }
    }
}
