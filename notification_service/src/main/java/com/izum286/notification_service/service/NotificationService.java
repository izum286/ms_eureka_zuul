package com.izum286.notification_service.service;

import com.izum286.notification_service.models.Message;

import javax.mail.MessagingException;

public interface NotificationService {
    void sendMessage(Message message) throws MessagingException;
}
