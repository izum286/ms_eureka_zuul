package com.izum286.carservice.model.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class PricePerDayEntity {

    private String currency;
    private float value;
}
