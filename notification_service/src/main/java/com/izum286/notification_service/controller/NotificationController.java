package com.izum286.notification_service.controller;

import com.izum286.notification_service.models.Message;
import com.izum286.notification_service.models.MessageType;
import com.izum286.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    NotificationService service;

    @PostMapping("send")
    public void sendMessage(@RequestBody Message message) throws MessagingException {
        service.sendMessage(message);
    }

    @GetMapping("test")
    public String test() throws MessagingException {
        System.out.println("*****************TEST INVOKED*****************");
        Message message = new Message(MessageType.MAIL,
                "testMessage",
                "some content",
                "i401623@gmail.com");
        service.sendMessage(message);
        return message.toString();
    }



}
