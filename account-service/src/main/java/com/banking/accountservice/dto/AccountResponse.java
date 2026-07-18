package com.banking.accountservice.dto;

import com.banking.accountservice.enumrate.AccountStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
public class AccountResponse {
     private Long id;
     private String accountHolderName;
     private String accountNumber;
     private String email;
     private String phoneNumber;
     private AccountStatus status;
     private BigDecimal balance;
     private LocalDateTime createdAt;


}
