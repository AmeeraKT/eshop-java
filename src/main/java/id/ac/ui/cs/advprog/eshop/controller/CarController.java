package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
class CarController {

    private final CarServiceImpl carService;

    @Autowired
    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping("/createCar")
    public String createCarPage (Model model) {

        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost (@ModelAttribute Car car, Model model) {
        carService.create(car);
        return "redirect:/car/listCar";
    }

    @GetMapping("/listCar")
    public String carListPage (Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);

        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage (@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);

        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost (@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carService.update(car.getCarId(), car);

        return "redirect:/car/listCar";
    }

    @GetMapping("/deleteCar/{carId}")
    public String deleteCar(@PathVariable String carId, Model model) {
        carService.deleteCarById(carId);

        return "redirect:/car/listCar";
    }
}