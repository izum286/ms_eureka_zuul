package com.izum286.search.Models;

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
