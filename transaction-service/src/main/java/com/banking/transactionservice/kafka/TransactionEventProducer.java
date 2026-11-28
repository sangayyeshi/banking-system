package com.banking.transactionservice.kafka;

import com.banking.common.events.TransactionCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {

    private final KafkaTemplate<String, TransactionCompletedEvent> kafkaTemplate;

    @Value("${app.kafka.topic:money-transferred}")
    private String topic;

    public void publishTransactionEvent(TransactionCompletedEvent event) {
        publishTransactionEvent(topic, event);
    }

    public void publishTransactionEvent(
            String targetTopic,
            TransactionCompletedEvent event) {

        kafkaTemplate.send(
                targetTopic,
                event.getTransactionId().toString(),
                event
        ).whenComplete((result, exception) -> {

            if (exception == null) {
                log.info(
                        "Event published successfully. Topic: {}, Transaction ID: {}",
                        targetTopic,
                        event.getTransactionId()
                );
            } else {
                log.error(
                        "Failed to publish event. Topic: {}, Transaction ID: {}",
                        targetTopic,
                        event.getTransactionId(),
                        exception
                );
            }
        });
    }
}

