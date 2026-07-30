package com.banking.messageservice.entity;

import com.banking.messageservice.enumrate.NotificationStatus;
import com.banking.messageservice.enumrate.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     @Column(nullable = false)
     private String recipient;
     @Column(nullable = false)
     private  String subject;
     @Column(nullable = false, length = 2000)
     private String message;
     @Enumerated(EnumType.STRING)
     private NotificationType type;
     @Enumerated(EnumType.STRING)
     private NotificationStatus status;
     private LocalDateTime createdAt;
}
