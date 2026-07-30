package com.banking.messageservice.service;

import com.banking.messageservice.dto.NotificationRequest;
import com.banking.messageservice.dto.NotificationResponse;
import com.banking.messageservice.entity.Notification;
import com.banking.messageservice.enumrate.NotificationStatus;
import com.banking.messageservice.repo.NotificationRepo;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationServiceImp implements NotificationService {
   private  final EmailService emailService;
     private final NotificationRepo notificationRepo;
    @Override
    public NotificationResponse sendNotification(NotificationRequest notificationRequest) {

        Notification notification = Notification.builder()
                .recipient(notificationRequest.getRecipient())
                .subject(notificationRequest.getSubject())
                .message(notificationRequest.getMessage())
                .type(notificationRequest.getType())
                .status(notificationRequest.getStatus())
                .createdAt(LocalDateTime.now())
                .build();

        try{
            emailService.sendEmail(notificationRequest);
            notification.setStatus(NotificationStatus.SENT);
            notificationRepo.save(notification);
            return NotificationResponse.builder()
                    .success(true)
                    .message("Notification sent successfully")
                    .build();
        }catch (Exception exception){
            notification.setStatus(NotificationStatus.FAILED);
            notificationRepo.save(notification);
            return  NotificationResponse.builder()
                    .success(false)
                    .message(exception.getMessage())
                    .build();
        }
    }
}
