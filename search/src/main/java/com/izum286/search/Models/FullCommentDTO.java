package com.izum286.search.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder


public class FullCommentDTO {

    private String first_name;

    private String second_name;

    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private LocalDateTime post_date;

    private String post;

    private String photo;
}
