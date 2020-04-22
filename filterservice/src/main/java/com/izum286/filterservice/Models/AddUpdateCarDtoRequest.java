package com.izum286.filterservice.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ApiModel(value = "AddUpdateCarDtoRequest",description = "Request from user to add or update existing car")
public class AddUpdateCarDtoRequest {
    @JsonProperty("serial_number")
    @NotNull
    private String serialNumber;
    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    private String year;
    @NotNull
    private String engine;
    @NotNull
    private String fuel;
    @NotNull
    private String gear;
    @NotNull
    @JsonProperty("wheels_drive")
    private String wheelsDrive;
    @NotNull
    @JsonProperty("horsepower")
    private float horsePower;
    @NotNull
    private float torque;
    @NotNull
    private int doors;
    @NotNull
    private int seats;
    @NotNull
    @JsonProperty("fuel_consumption")
    private float fuelConsumption;
    @NotNull
    private List<Object> features;
    @NotNull
    @JsonProperty("car_class")
    private String carClass;
    @NotNull
    @JsonProperty("price_per_day")
    private double pricePerDay;
    @NotNull
    @JsonProperty("distance_included")
    private int distanceIncluded;
    @NotNull
    private String about;
    @NotNull
    @JsonProperty("pick_up_place")
    private Object pickUpPlaceDto;
    @NotNull
    @JsonProperty("image_url")
    private List<String> imageUrl;
}
