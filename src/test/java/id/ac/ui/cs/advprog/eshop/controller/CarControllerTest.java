package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarServiceImpl carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;
    private Car testCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        testCar = new Car();
        testCar.setCarId("CAR-01");
        testCar.setCarName("Tesla");
        testCar.setCarColor("Red");
        testCar.setCarQuantity(10);

        model = mock(Model.class);
    }

    @Test
    void testCreateCarPage() {
        String result = carController.createCarPage(model);

        assertEquals("createCar", result);
        verify(model).addAttribute(eq("car"), any(Car.class));
    }

    @Test
    void testCreateCarPost() {
        String result = carController.createCarPost(testCar, model);

        assertEquals("redirect:/car/listCar", result);
        verify(carService).create(testCar);
    }

    @Test
    void testCarListPage() {
        List<Car> carList = Collections.singletonList(testCar);
        when(carService.findAll()).thenReturn(carList);

        String result = carController.carListPage(model);
        assertEquals("carList", result);
        verify(model).addAttribute("cars", carList);
    }

    @Test
    void testEditCarPage() {
        when(carService.findById("CAR-01")).thenReturn(testCar);
        String result = carController.editCarPage("CAR-01", model);

        assertEquals("editCar", result);
        verify(model).addAttribute("car", testCar);
        verify(carService).findById("CAR-01");
    }
    @Test
    void testEditCarPost() {
        String result = carController.editCarPost(testCar, model);

        assertEquals("redirect:/car/listCar", result);
        verify(carService).update(testCar.getCarId(), testCar);
    }
    @Test
    void testDeleteCar() throws Exception {
        mockMvc.perform(get("/car/deleteCar/CAR-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));

        verify(carService, times(1)).deleteCarById("CAR-01");
    }
}
