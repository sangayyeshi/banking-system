package com.banking.authservice.client;

import com.banking.authservice.dto.NotificationRequest;

import com.banking.authservice.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient( name = "message-service")
public interface MessageClient {
    @PostMapping("/api/messages/send")
    NotificationResponse sendMessage(@RequestBody NotificationRequest notificationRequest);
}
