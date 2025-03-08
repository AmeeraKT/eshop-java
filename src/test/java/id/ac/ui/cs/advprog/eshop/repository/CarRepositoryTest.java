package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {

    private CarRepositoryImpl carRepository;
    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepositoryImpl();

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
    void testCreateCarWithId() {
        Car createdCar = carRepository.create(car1);

        assertNotNull(createdCar);
        assertEquals("CAR-01", createdCar.getCarId());

        // convert iterator to list
        List<Car> carList = new ArrayList<>();
        carRepository.findAll().forEachRemaining(carList::add);

        assertEquals(1, carList.size());
    }

    @Test
    void testFindAllCars() {
        carRepository.create(car1);
        carRepository.create(car2);

        // convert iterator to list
        List<Car> carList = new ArrayList<>();
        carRepository.findAll().forEachRemaining(carList::add);

        assertEquals(2, carList.size());
    }

    @Test
    void testFindCarById() {
        carRepository.create(car1);

        Car foundCar = carRepository.findById("CAR-01");

        assertNotNull(foundCar);
        assertEquals("CAR-01", foundCar.getCarId());
        assertEquals("Bumblebee", foundCar.getCarName());
        assertEquals("Yellow", foundCar.getCarColor());
        assertEquals(100, foundCar.getCarQuantity());
    }

    @Test
    void testFindCarByIdNotFound() {
        Car result = carRepository.findById("FAKEID");

        assertNull(result);
    }

    @Test
    void testUpdateExistingCar() {
        carRepository.create(car1);

        Car updatedCar = new Car();
        updatedCar.setCarId("CAR-01");
        updatedCar.setCarName("Bumblebee Updated");
        updatedCar.setCarColor("Gold");
        updatedCar.setCarQuantity(150);

        Car result = carRepository.update("CAR-01", updatedCar);

        assertNotNull(result);
        assertEquals("Bumblebee Updated", result.getCarName());
        assertEquals("Gold", result.getCarColor());
        assertEquals(150, result.getCarQuantity());
    }

    @Test
    void testUpdateNonExistentCar() {
        Car updatedCar = new Car();
        updatedCar.setCarId("FAKEID");
        updatedCar.setCarName("Unknown");
        updatedCar.setCarColor("Gray");
        updatedCar.setCarQuantity(0);

        Car result = carRepository.update("FAKEID", updatedCar);

        assertNull(result);
    }

    @Test
    void testDeleteExistingCar() {
        carRepository.create(car1);

        carRepository.delete("CAR-01");

        assertNull(carRepository.findById("CAR-01"));
    }

    @Test
    void testDeleteNonExistentCar() {
        assertDoesNotThrow(() -> carRepository.delete("FAKEID"));
    }

    // reject car creation if no id is given
    @Test
    void testCreateCarWithNullIdThrowsException() {
        Car carWithoutId = new Car();
        carWithoutId.setCarName("Tesla");
        carWithoutId.setCarColor("Red");
        carWithoutId.setCarQuantity(10);

        assertThrows(IllegalArgumentException.class, () -> carRepository.create(carWithoutId));
    }

    // update car and find the same car in a list
    @Test
    void testUpdateCarEnsuresLoopRunsAndFindsCorrectCar() {
        carRepository.create(car1);
        carRepository.create(car2);

        Car updatedCar = new Car();
        updatedCar.setCarId("CAR-01");
        updatedCar.setCarName("Updated Car");
        updatedCar.setCarColor("Silver");
        updatedCar.setCarQuantity(20);

        Car result = carRepository.update("CAR-01", updatedCar);

        assertNotNull(result);
        assertEquals("Updated Car", result.getCarName());
        assertEquals("Silver", result.getCarColor());
        assertEquals(20, result.getCarQuantity());

        // Ensure car2 is unchanged (proving the loop worked)
        Car unchangedCar = carRepository.findById("CAR-02");
        assertNotNull(unchangedCar);
        assertEquals("Bat Mobile", unchangedCar.getCarName());
    }

    @Test
    void testUpdateCarWithNonExistentId() {
        carRepository.create(car1);

        Car updatedCar = new Car();
        updatedCar.setCarId("FAKEID");
        updatedCar.setCarName("Fake Car");
        updatedCar.setCarColor("Invisible");
        updatedCar.setCarQuantity(0);

        Car result = carRepository.update("FAKEID", updatedCar);

        assertNull(result);
    }
}
