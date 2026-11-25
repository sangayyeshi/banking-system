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

        kafkaTemplate.send(topic, event.getTransactionId().toString(), event)
                .whenComplete((result, exception) -> {
                    if (exception == null) {
                        log.info(
                                "Transaction event published successfully. Transaction ID: {}",
                                event.getTransactionId()
                        );
                    } else {
                        log.error(
                                "Failed to publish transaction event. Transaction ID: {}",
                                event.getTransactionId(),
                                exception
                        );
                    }
                });
    }
}


