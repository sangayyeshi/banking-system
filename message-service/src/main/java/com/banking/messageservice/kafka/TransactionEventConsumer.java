package com.banking.messageservice.kafka;

import com.banking.common.events.TransactionCompletedEvent;
import com.banking.messageservice.dto.NotificationRequest;
import com.banking.messageservice.dto.NotificationResponse;
import com.banking.messageservice.enumrate.NotificationType;
import com.banking.messageservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionEventConsumer {
     private final NotificationService notificationService;


    @KafkaListener(
            topics = "${app.kafka.topic:money-transferred}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTransactionEvent(TransactionCompletedEvent event) {

        log.info("Received transaction event: {}", event);

        String subject = "Bank Transaction Notification";

        String message = String.format(
                "Hello %s,\n\n" +
                        "Your %s transaction was completed successfully.\n\n" +
                        "Transaction ID: %s\n" +
                        "Amount: %s KD\n" +
                        "Account Number: %s\n" +
                        "Description: %s\n" +
                        "Other Account: %s\n" +
                        "Remaining Balance: %s KD\n\n" +
                        "Thank you for banking with us.",
                event.getRecipientName(),
                event.getTransactionType(),
                event.getTransactionId(),
                event.getAmount(),
                event.getRecipientAccountNumber(),
                event.getDescription(),
                event.getOtherPartyAccountNumber() == null
                        ? "N/A"
                        : event.getOtherPartyAccountNumber(),
                event.getBalanceAfterTransaction()
        );

        NotificationRequest request = NotificationRequest.builder()
                .recipient(event.getRecipientEmail())
                .subject(subject)
                .message(message)
                .type(NotificationType.EMAIL)
                .build();

        NotificationResponse response =
                notificationService.sendNotification(request);

        if (response == null || !response.isSuccess()) {
            throw new IllegalStateException(
                    "Notification failed: " +
                            (response == null ? "No response" : response.getMessage())
            );
        }

        log.info(
                "Notification processed successfully for transaction ID: {}",
                event.getTransactionId()
        );
    }
}
