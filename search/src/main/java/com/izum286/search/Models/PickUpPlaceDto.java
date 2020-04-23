package com.izum286.search.Models;

import lombok.*;

/**
 * @author vitalii_adler
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PickUpPlaceDto {
    private String place_id;
    private double latitude;
    private double longitude;
}
