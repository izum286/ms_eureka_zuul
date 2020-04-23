package com.izum286.search.Models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PersonWhoBookedEntity {
    private String email;
    private String first_name;
    private String second_name;
    private String phone;
}
