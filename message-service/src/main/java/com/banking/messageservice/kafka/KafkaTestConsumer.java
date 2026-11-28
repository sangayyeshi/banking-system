package com.banking.messageservice.kafka;

import com.banking.common.events.TransactionCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Profile("dev")
public class KafkaTestConsumer {
    @KafkaListener(
            topics = "kafka-test-events",
            groupId = "kafka-test-verification"
    )
    public void consumeTestEvent(TransactionCompletedEvent event) {

        log.info("========================================");
        log.info("KAFKA TEST EVENT RECEIVED SUCCESSFULLY!");
        log.info("Transaction ID: {}", event.getTransactionId());
        log.info("Transaction Type: {}", event.getTransactionType());
        log.info("Amount: {}", event.getAmount());
        log.info("Description: {}", event.getDescription());
        log.info("Recipient: {}", event.getRecipientName());
        log.info("========================================");
    }
}
