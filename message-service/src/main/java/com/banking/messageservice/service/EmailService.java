package com.banking.messageservice.service;

import com.banking.messageservice.dto.NotificationRequest;

public interface EmailService {
    void sendEmail(NotificationRequest notificationRequest);
}
