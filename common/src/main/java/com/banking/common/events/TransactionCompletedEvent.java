package com.banking.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCompletedEvent {
    private Long transactionId;

    private String transactionType;

    private BigDecimal amount;

    private String description;

    private LocalDateTime createdAt;

    // Notification recipient details
    private String recipientEmail;

    private String recipientName;

    private String recipientAccountNumber;

    private String otherPartyAccountNumber;

    private BigDecimal balanceAfterTransaction;
}



