package com.banking.transactionservice.service;


import com.banking.common.Core.AccountUpdateRequest;
import com.banking.transactionservice.dto.TransactionRequest;
import com.banking.transactionservice.dto.TransactionResponse;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.enumrate.TransactionType;
import com.banking.transactionservice.feign.AccountClient;
import com.banking.transactionservice.repo.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
     private final TransactionRepo transactionRepo;
     private final AccountClient accountClient;


public TransactionResponse deposit(TransactionRequest transactionRequest) {
    com.banking.common.Core.AccountResponse
            accountResponse =accountClient.getAccountById(transactionRequest.getAccountId());
     BigDecimal newBalance = accountResponse.getBalance().add(transactionRequest.getAmount());
    AccountUpdateRequest accountUpdateRequest =  AccountUpdateRequest
            .builder()
            .amount(newBalance)
            .build();
    accountClient.updateAccount(accountResponse.getId(),accountUpdateRequest);
    Transaction transaction =  Transaction
            .builder().accountId(accountResponse.getId())
            .type(TransactionType.DEPOSIT)
            .amount(transactionRequest.getAmount())
            .description(transactionRequest.getDescription())
            .createdAt(LocalDateTime.now())
            .build();
     Transaction saved = transactionRepo.save(transaction);
       return map(saved);
}

   public TransactionResponse map(Transaction transaction) {
        return  TransactionResponse.builder()
        .id(transaction.getId())
        .accountId(transaction.getAccountId())
        .amount(transaction.getAmount())
        .description(transaction.getDescription())
        .createdAt(transaction.getCreatedAt())
        .build();}
}
