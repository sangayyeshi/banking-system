package com.banking.transactionservice.entity;

import com.banking.transactionservice.enumrate.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "transactions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private Long accountId;;
     private Long receiverAccountId;
     @Enumerated(EnumType.STRING)
     private TransactionType type;
     private BigDecimal amount;
     private String description;
     private LocalDateTime createdAt;
}
