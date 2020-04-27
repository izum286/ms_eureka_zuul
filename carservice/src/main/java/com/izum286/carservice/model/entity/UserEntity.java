package com.izum286.carservice.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.izum286.carservice.model.dto.LocationDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vitalii_adler
 * @author Konkin Anton
 *
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_entity")
public class UserEntity {
    @Id
    private String email;
    private String driverLicense;
    private String firstName;
    private String lastName;
    private LocationDTO location;
    private String phone;
    private boolean isDeleted;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime registrationDate;
    private String photo;
    private List<CommentEntity> comments;
    private List<String> ownCars; //List of carNumbers
    private List<BookedPeriodEntity> history;
    private List<BookedPeriodEntity> bookedCars;

}

