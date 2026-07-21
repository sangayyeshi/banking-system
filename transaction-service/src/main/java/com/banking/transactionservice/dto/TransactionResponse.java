package com.banking.transactionservice.dto;

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
public class TransactionResponse {
      private Long id;
      private Long accountId;
      private Long receiverAccountId;
      private BigDecimal amount;
      private String description;
      private LocalDateTime createdAt;
}
