package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    Car car;
    @BeforeEach
    void setUp() {

        this.car = new Car();
        this.car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.car.setCarName("Bumblebee");
        this.car.setCarColor("Yellow");
        this.car.setCarQuantity(100);
    }

    @Test
    void testGetCarId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Bumblebee", this.car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Yellow", this.car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(100, this.car.getCarQuantity());
    }
}