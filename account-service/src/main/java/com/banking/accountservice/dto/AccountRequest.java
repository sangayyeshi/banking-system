package com.banking.accountservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AccountRequest {
     @NotBlank
     private String accountHolderName;
     private String accountNumber;
     @Email
     private String email;
     private BigDecimal balance;
     private  String phoneNumber;

}
