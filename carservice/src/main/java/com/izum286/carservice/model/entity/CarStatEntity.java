package com.izum286.carservice.model.entity;

import lombok.*;

/**
 * @author Gor Aleks
 * 03.01.2020
 */
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
