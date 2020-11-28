package ar.edu.iua.rest;


import ar.edu.iua.business.ConciliacionBusiness;
import ar.edu.iua.business.ProductoBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.model.Conciliacion;
import ar.edu.iua.model.Producto;
import ar.edu.iua.model.persistence.ConciliacionRepository;
import ar.edu.iua.model.persistence.ProductoRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ConciliacionRestController.class)
public class ConciliacionRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConciliacionRepository conciliacionDAO;

    @MockBean
    private ConciliacionBusiness conciliacionBusiness;

    private static Conciliacion conciliacion1;


    @BeforeClass
    public static void setup() {
        conciliacion1 = new Conciliacion();
        conciliacion1.setId(1);
        conciliacion1.setCaudal(12);
        conciliacion1.setDensidad(0.8);
        conciliacion1.setTemperatura(24);
        conciliacion1.setDiferenciaBalanzaCaudalimetro(10);
        conciliacion1.setNetoBalanza(10010);
        conciliacion1.setProductoCargado(10000);
        conciliacion1.setPesajeInicial(10000);
        conciliacion1.setPesajeFinal(20010);
        conciliacion1.setNumeroOrden("000001");
    }

//    @Test
//    public void testListSuccess()
//            throws BusinessException, Exception {
//
//        List<Conciliacion> conciliaciones = new ArrayList<Conciliacion>();
//        conciliaciones.add(conciliacion1);
//
//
//        when(conciliacionBusiness.list()).thenReturn(conciliaciones);
//
//        mvc.perform(get("/api/v1/conciliaciones/")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(conciliacion1.getId())));
//    }

//    @Test
//    public void testLoadByDescription()
//            throws BusinessException, Exception {
//
//        Producto conciliacionDescContains = prod1;
//        String description = "mito";
//
//
//        when(conciliacionBusiness.findByDescripcionContains(description)).thenReturn(conciliacionDescContains);
//
//        mvc.perform(get("/api/v1/conciliaciones/description")
//                .param("desc", description)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath(("$.id"),  is(prod1.getId()), Long.class))
//                .andExpect(jsonPath("$.descripcion", is(prod1.getDescripcion())));
//
//    }
}
