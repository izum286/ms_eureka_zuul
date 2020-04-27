package com.izum286.carservice.controller;

import com.izum286.carservice.model.dto.*;
import com.izum286.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;



@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("car")
    public FullCarDTOResponse addCar(@RequestBody AddUpdateCarDtoRequest carDTO, Principal principal) {
        String userEmail = principal.getName();
        //filterService.addFilter(carDTO); remove to service -> into addCar method throw FilterClient invocation
        return carService.addCar(carDTO, userEmail).orElseThrow(() -> new RuntimeException(String.format("Car %s already exist", carDTO.getSerialNumber())));
    }

    //**********************************************************************************


    @PutMapping("/car")
    public FullCarDTOResponse updateCar(@RequestParam("serial_number") String serial_number, @RequestBody AddUpdateCarDtoRequest carDTO, Principal principal) {
        //TODO check serial inside dto and serial_number or ->
        carDTO.setSerialNumber(serial_number);
        //filterService.addFilter(carDTO); remove to service -> into addCar method throw FilterClient invocation
        String userEmail = principal.getName();
        //TODO add correct exception
        return carService.updateCar(carDTO, userEmail).orElseThrow();
    }


    //**********************************************************************************


    @DeleteMapping("/car")
    public void deleteCar(@RequestParam(name = "serial_number") String carId, Principal principal) {
        String userEmail = principal.getName();
        carService.deleteCar(carId, userEmail);
    }


    //**********************************************************************************

    @GetMapping("/car")
    public FullCarDTOResponse getCarByIdForUsers(@RequestParam(name = "serial_number") String carId) {
        return carService.getCarByIdForUsers(carId).orElseThrow();
    }

    //**********************************************************************************


    @GetMapping("/user/cars")
    public List<FullCarDTOResponse> ownerGetCars(Principal principal) {
        String userEmail = principal.getName();
        return carService.ownerGetCars(userEmail);
    }


    //**********************************************************************************

    public FullCarDTOResponse getCarByIdForOwner(@RequestParam(name = "serial_number") String carId,
                                                 Principal principal) {
        String userEmail = principal.getName();
        return carService.getCarByIdForOwner(carId, userEmail).orElseThrow();
    }


    //**********************************************************************************




    @GetMapping("/user/cars/car")
    public FullCarDTOResponse ownerGetCarById(@RequestParam(name = "serial_number") String carId) {
        return carService.getCarByIdForUsers(carId).orElseThrow();
    }


    //**********************************************************************************


    @GetMapping("/user/cars/periods")
    public List<BookedPeriodDto> ownerGetBookedPeriodsByCarId(@RequestParam(name = "serial_number") String carId,
                                                              Principal principal) {
        String userEmail = principal.getName();
        return carService.getBookedPeriodsByCarId(carId, userEmail);
    }


    @PostMapping("/car/reservation")
    public BookResponseDTO makeReservation(@RequestParam(name = "serial_number") String carId,
                                           @RequestBody BookRequestDTO dto, Principal principal) {
        String userEmail = principal.getName();
        return carService.makeReservation(carId, dto, userEmail).orElseThrow();
    }

    @GetMapping("/car/best")
    public List<FullCarDTOResponse> getThreeBestCars() {
        return carService.getThreeBestCars();
    }

}
