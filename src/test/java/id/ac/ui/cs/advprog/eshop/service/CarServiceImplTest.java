package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {

        this.car1 = new Car();
        this.car1.setCarId("CAR-01");
        this.car1.setCarName("Bumblebee");
        this.car1.setCarColor("Yellow");
        this.car1.setCarQuantity(100);

        this.car2 = new Car();
        this.car2.setCarId("CAR-02");
        this.car2.setCarName("Bat Mobile");
        this.car2.setCarColor("Black");
        this.car2.setCarQuantity(1);
    }

    @Test
    void testCreateCar() {
        carService.create(car1);
        verify(carRepository, times(1)).create(car1);
    }

    @Test
    void testFindAllCars() {
        Iterator<Car> carIterator = Arrays.asList(car1, car2).iterator();
        when(carRepository.findAll()).thenReturn(carIterator);

        List<Car> result = carService.findAll();

        assertEquals(2, result.size());
        assertEquals(car1.getCarId(), result.get(0).getCarId());
        assertEquals(car2.getCarId(), result.get(1).getCarId());

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testFindCarById() {
        when(carRepository.findById("CAR-01")).thenReturn(car1);

        Car result = carService.findById("CAR-01");

        assertNotNull(result);
        assertEquals("CAR-01", result.getCarId());

        verify(carRepository, times(1)).findById("CAR-01");
    }

    @Test
    void testFindCarByIdNotFound() {
        when(carRepository.findById(anyString())).thenReturn(null);

        Car result = carService.findById("NON_EXISTENT");

        assertNull(result);
        verify(carRepository, times(1)).findById("NON_EXISTENT");
    }

    @Test
    void testUpdateCar() {
        carService.update("CAR-01", car1);
        verify(carRepository, times(1)).update("CAR-01", car1);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("CAR-01");
        verify(carRepository, times(1)).delete("CAR-01");
    }
}
