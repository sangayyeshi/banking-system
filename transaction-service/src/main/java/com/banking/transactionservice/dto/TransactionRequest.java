package com.banking.transactionservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotNull(message = "Account ID is required")
    private Long accountId;
    @NotNull(message = "Amount is required ")
    @DecimalMin(value = "0.01" ,message = "Amount must be greater then zero")
    private BigDecimal amount;
    private String description;
}
