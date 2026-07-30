package com.banking.messageservice.dto;

import com.banking.messageservice.enumrate.NotificationStatus;
import com.banking.messageservice.enumrate.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class NotificationRequest {
    @Email
    @NotBlank
    private String recipient;
    @NotBlank
    private String subject;
    @NotBlank
    private String message;
    private NotificationStatus status;
    private NotificationType type;
}
