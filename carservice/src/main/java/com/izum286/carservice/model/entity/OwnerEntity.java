package com.izum286.carservice.model.entity;

import lombok.*;

import java.time.LocalDateTime;

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
public class OwnerEntity {
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime registrationDate;
}
