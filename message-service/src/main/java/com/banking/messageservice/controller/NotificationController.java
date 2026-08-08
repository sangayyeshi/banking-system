package com.banking.messageservice.controller;

import com.banking.messageservice.dto.NotificationRequest;
import com.banking.messageservice.dto.NotificationResponse;
import com.banking.messageservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class NotificationController {
     private final NotificationService notificationService;

     @PostMapping("/send")
     public ResponseEntity<NotificationResponse> sendNotification( @Valid @RequestBody NotificationRequest notificationRequest) {
          return   ResponseEntity.ok(notificationService.sendNotification(notificationRequest));
     }

}
