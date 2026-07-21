package com.banking.common.Core;
import com.banking.common.enumrate.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
 @Data
 @Builder
 @NoArgsConstructor
 @AllArgsConstructor
public class AccountResponse {

     private Long id;
     private String accountHolderName;
     private String accountNumber;
     private String email;
     private String phoneNumber;
     private AccountStatus status;
     private BigDecimal balance;
}
