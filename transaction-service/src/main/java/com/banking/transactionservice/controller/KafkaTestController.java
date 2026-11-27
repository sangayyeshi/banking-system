package com.banking.transactionservice.controller;

import com.banking.common.events.TransactionCompletedEvent;
import com.banking.transactionservice.kafka.TransactionEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/kafka-test")
@RequiredArgsConstructor
@Profile("dev")
public class KafkaTestController {
    private final TransactionEventProducer producer;




    @PostMapping("/publish")
    public Map<String, String> publishTestEvent() {

        TransactionCompletedEvent event = TransactionCompletedEvent.builder()
                .transactionId(999L)
                .transactionType("TEST")
                .amount(new BigDecimal("10.00"))
                .description("Kafka integration test")
                .createdAt(LocalDateTime.now())
                .recipientEmail("test@example.com")
                .recipientName("Kafka Test")
                .recipientAccountNumber("TEST-001")
                .otherPartyAccountNumber(null)
                .balanceAfterTransaction(new BigDecimal("100.00"))
                .build();

        producer.publishTransactionEvent("kafka-test-events", event);

        return Map.of(
                "status", "submitted",
                "topic", "kafka-test-events",
                "message", "Test event submitted to Kafka"
        );
    }
}
