package com.banking.transactionservice.feign;

import com.banking.transactionservice.dto.NotificationRequest;
import com.banking.transactionservice.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service")
public interface MessageClient {

        @PostMapping("/api/messages/send")
        NotificationResponse sendNotification(
                @RequestBody NotificationRequest request
        );
}
