package com.banking.accountservice.entity;

import com.banking.common.enumrate.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "accounts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String accountNumber;
     private String accountHolderName;
     private String email;
     private String phoneNumber;
     private BigDecimal balance;
     @Enumerated(EnumType.STRING)
     private AccountStatus accountStatus;
     private LocalDateTime createdAt;
     private LocalDateTime updatedAt;

}
