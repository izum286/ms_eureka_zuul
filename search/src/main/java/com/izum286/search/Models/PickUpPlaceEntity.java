package com.izum286.search.Models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PickUpPlaceEntity {
    private String place_id;
    private double latitude;
    private double longitude;
}
