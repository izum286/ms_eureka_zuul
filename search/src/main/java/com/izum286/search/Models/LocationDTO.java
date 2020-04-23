package com.izum286.search.Models;

import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDTO {
    private String country;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String lat;
    private String lon;
    private boolean isVehicle;
}
