package com.izum286.notification_service.service;

import com.izum286.notification_service.models.LoggerInfo;
import com.izum286.notification_service.models.Message;
import com.izum286.notification_service.repository.LoggerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    JavaMailSender sender;

    @Autowired
    LoggerRepo repo;

    @Override
    public void sendMessage(Message message) throws MessagingException {
        MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(message.getRecepients());
        helper.setFrom("c32068@mail.ru");
        helper.setSubject(message.getSubject());
        helper.setText(message.getMessageContent());
        sender.send(msg);
        log.info("{}  notification has been send to {}", message.getType(), message.getRecepients());
        LoggerInfo info = new LoggerInfo(message.getType().toString() +
                " notification was sent to "+message.getRecepients() + " at "+LocalDateTime.now());
        repo.save(info);
    }
}
