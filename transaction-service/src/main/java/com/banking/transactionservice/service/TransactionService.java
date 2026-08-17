package com.banking.transactionservice.service;


import com.banking.common.Core.AccountResponse;
import com.banking.common.Core.AccountUpdateRequest;
import com.banking.common.expections.InsufficientBalanceException;
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

    // 1. Deposit
    public TransactionResponse deposit(TransactionRequest transactionRequest) {
        com.banking.common.Core.AccountResponse
                accountResponse = accountClient.getAccountById(transactionRequest.getAccountId());
        BigDecimal newBalance = accountResponse.getBalance().add(transactionRequest.getAmount());
        AccountUpdateRequest accountUpdateRequest = AccountUpdateRequest
                .builder()
                .amount(newBalance)
                .build();
        accountClient.updateAccount(accountResponse.getId(), accountUpdateRequest);
        Transaction transaction = Transaction
                .builder().accountId(accountResponse.getId())
                .type(TransactionType.DEPOSIT)
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        Transaction saved = transactionRepo.save(transaction);
        return map(saved);
    }

    // 2.Withdraw
    public TransactionResponse withdraw(TransactionRequest transactionRequest) {

        // Get the account
        AccountResponse accountResponse =
                accountClient.getAccountById(
                        transactionRequest.getAccountId()
                );

        // Check sufficient balance
        if (accountResponse.getBalance()
                .compareTo(transactionRequest.getAmount()) < 0) {

            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        // Calculate new balance
        BigDecimal newBalance =
                accountResponse.getBalance()
                        .subtract(transactionRequest.getAmount());

        // Update account
        AccountUpdateRequest accountUpdateRequest =
                AccountUpdateRequest.builder()
                        .amount(newBalance)
                        .build();

        accountClient.updateAccount(
                accountResponse.getId(),
                accountUpdateRequest
        );

        // Create transaction
        Transaction transaction =
                Transaction.builder()
                        .accountId(accountResponse.getId())
                        .type(TransactionType.WITHDRAW)
                        .amount(transactionRequest.getAmount())
                        .description(transactionRequest.getDescription())
                        .createdAt(LocalDateTime.now())
                        .build();

        // Save transaction
        Transaction saved =
                transactionRepo.save(transaction);

        return map(saved);
    }

    // 3. Transfer
    public TransactionResponse transfer(TransactionRequest transactionRequest) {
        // Get the account
        AccountResponse fromAccount = accountClient.getAccountById(transactionRequest.getAccountId());
        //check the balance
        if (fromAccount.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        // get the designation account
        AccountResponse toAccount = accountClient.getAccountById(transactionRequest.getReceiverAccountId());
        // calculate the new balance
        BigDecimal fromNewAccount = fromAccount.getBalance().subtract(transactionRequest.getAmount());
        BigDecimal toNewAccount = toAccount.getBalance().add(transactionRequest.getAmount());
        // Update source account
        AccountUpdateRequest updateRequest = AccountUpdateRequest.builder()
                .amount(fromNewAccount)
                .build();
        accountClient.updateAccount(fromAccount.getId(), updateRequest);
        //Update designation account
        AccountUpdateRequest accountUpdateRequest = AccountUpdateRequest
                .builder()
                .amount(toNewAccount)
                .build();
        accountClient.updateAccount(toAccount.getId(), accountUpdateRequest);
        // save the transaction
        Transaction transaction = Transaction.builder()
                .accountId(fromAccount.getId())
                .type(TransactionType.TRANSFER)
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        Transaction saved = transactionRepo.save(transaction);
        return map(saved);
    }

    public TransactionResponse map(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}

