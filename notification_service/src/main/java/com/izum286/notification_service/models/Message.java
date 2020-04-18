package com.izum286.notification_service.models;


import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private MessageType type;
    private String subject;
    private String messageContent;
    private String recepients;
}
