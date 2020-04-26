package com.izum286.carservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookedPeriodDto {
    private String order_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start_date_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end_date_time;
    private Boolean paid;
    private Float amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private String booking_date;
    PersonWhoBookedDto person_who_booked;
}
