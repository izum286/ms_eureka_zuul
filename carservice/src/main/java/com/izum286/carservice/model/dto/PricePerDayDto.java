package com.izum286.carservice.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class PricePerDayDto {
    private String currency;
    private float value;
}
