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

        Long fromAccountId = transactionRequest.getAccountId();
        Long toAccountId = transactionRequest.getReceiverAccountId();
        BigDecimal amount = transactionRequest.getAmount();

        // Prevent transferring to the same account
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException(
                    "Sender and receiver accounts cannot be the same"
            );
        }

        // 1. Debit sender atomically
        AccountResponse fromAccount =
                accountClient.debit(fromAccountId, amount);

        try {

            // 2. Credit receiver
            AccountResponse toAccount =
                    accountClient.credit(toAccountId, amount);

            // 3. Create transaction record
            Transaction transaction =
                    Transaction.builder()
                            .accountId(fromAccount.getId())
                            .receiverAccountId(toAccount.getId())
                            .type(TransactionType.TRANSFER)
                            .amount(amount)
                            .description(transactionRequest.getDescription())
                            .createdAt(LocalDateTime.now())
                            .build();

            Transaction saved =
                    transactionRepo.save(transaction);

            return map(saved);

        } catch (Exception e) {

            // Receiver credit failed.
            // Compensate by returning the money to sender.
            try {
                accountClient.credit(fromAccountId, amount);
            } catch (Exception compensationException) {
                compensationException.printStackTrace();
            }

            throw new RuntimeException(
                    "Transfer failed and compensation was attempted",
                    e
            );
        }
    }

    public TransactionResponse map(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .receiverAccountId(transaction.getReceiverAccountId())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}

