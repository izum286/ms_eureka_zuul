package com.izum286.search.Models;

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
