package com.banking.messageservice.service;

import com.banking.messageservice.dto.NotificationRequest;
import com.banking.messageservice.dto.NotificationResponse;

public interface NotificationService {
   NotificationResponse sendNotification(NotificationRequest notificationRequest);
}
