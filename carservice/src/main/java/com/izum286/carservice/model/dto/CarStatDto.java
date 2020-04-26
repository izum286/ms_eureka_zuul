package com.izum286.carservice.model.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarStatDto {
    private int trips;
    private double rating;
}
