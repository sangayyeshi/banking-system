package com.banking.messageservice.service;

import com.banking.messageservice.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImp implements EmailService {
    private final JavaMailSender mailSender;
    @Override
    public void sendEmail(NotificationRequest notificationRequest) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(notificationRequest.getRecipient());
        mailMessage.setSubject(notificationRequest.getSubject());
        mailMessage.setText(notificationRequest.getMessage());
        mailSender.send(mailMessage);

    }
}
