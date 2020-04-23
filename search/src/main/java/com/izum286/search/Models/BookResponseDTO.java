package com.izum286.search.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class BookResponseDTO {
    private String order_number;
    private float amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String booking_date;
}