package com.izum286.carservice.model.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CarStatEntity {
    private int trips;
    private double rating;
}
