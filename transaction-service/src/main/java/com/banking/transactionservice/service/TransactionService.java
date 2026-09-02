package com.banking.transactionservice.service;

import com.banking.common.Core.AccountResponse;
import com.banking.transactionservice.dto.NotificationRequest;
import com.banking.transactionservice.dto.TransactionRequest;
import com.banking.transactionservice.dto.TransactionResponse;
import com.banking.transactionservice.entity.Transaction;
import com.banking.transactionservice.enumrate.TransactionType;
import com.banking.transactionservice.feign.AccountClient;
import com.banking.transactionservice.feign.MessageClient;
import com.banking.transactionservice.repo.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountClient accountClient;
    private final MessageClient messageClient;

    // 1. Deposit / Credit
    public TransactionResponse deposit(TransactionRequest transactionRequest) {

        AccountResponse accountResponse = accountClient.credit(
                transactionRequest.getAccountId(),
                transactionRequest.getAmount()
        );

        Transaction transaction = Transaction.builder()
                .accountId(accountResponse.getId())
                .type(TransactionType.DEPOSIT)
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepo.save(transaction);

        // Send deposit notification
        sendDepositNotification(accountResponse, transactionRequest);

        return map(saved);
    }

    // 2. Withdraw / Debit
    public TransactionResponse withdraw(TransactionRequest transactionRequest) {

        AccountResponse accountResponse = accountClient.debit(
                transactionRequest.getAccountId(),
                transactionRequest.getAmount()
        );

        Transaction transaction = Transaction.builder()
                .accountId(accountResponse.getId())
                .type(TransactionType.WITHDRAW)
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepo.save(transaction);

        // Send withdrawal notification
        sendWithdrawalNotification(accountResponse, transactionRequest);

        return map(saved);
    }

    // 3. Transfer
    public TransactionResponse transfer(TransactionRequest transactionRequest) {

        AccountResponse fromAccount = accountClient.getAccountById(
                transactionRequest.getAccountId()
        );

        AccountResponse toAccount = accountClient.getAccountById(
                transactionRequest.getReceiverAccountId()
        );

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new IllegalArgumentException(
                    "Cannot transfer money to the same account"
            );
        }

        accountClient.debit(
                fromAccount.getId(),
                transactionRequest.getAmount()
        );

        try {

            accountClient.credit(
                    toAccount.getId(),
                    transactionRequest.getAmount()
            );

        } catch (Exception e) {

            accountClient.credit(
                    fromAccount.getId(),
                    transactionRequest.getAmount()
            );

            throw e;
        }

        Transaction transaction = Transaction.builder()
                .accountId(fromAccount.getId())
                .receiverAccountId(toAccount.getId())
                .type(TransactionType.TRANSFER)
                .amount(transactionRequest.getAmount())
                .description(transactionRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepo.save(transaction);

        return map(saved);
    }

    // 4. Get transaction history by account
    public List<TransactionResponse> getTransactionsByAccountId(Long accountId) {

        return transactionRepo
                .findByAccountIdOrReceiverAccountId(accountId, accountId)
                .stream()
                .map(this::map)
                .toList();
    }

    // Map Transaction → TransactionResponse
    public TransactionResponse map(Transaction transaction) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .receiverAccountId(transaction.getReceiverAccountId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getType().name())
                .description(transaction.getDescription())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    // Deposit notification
    private void sendDepositNotification(
            AccountResponse account,
            TransactionRequest request) {

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .recipient(account.getEmail())
                .subject("Deposit Successful")
                .message(
                        "Dear " + account.getAccountHolderName() + ",\n\n" +
                                "Your deposit was successful.\n\n" +
                                "Amount: " + request.getAmount() + "\n" +
                                "Account Number: " + account.getAccountNumber() + "\n" +
                                "Description: " + request.getDescription() + "\n" +
                                "New Balance: " + account.getBalance() + "\n\n" +
                                "Thank you for using our banking service."
                )
                .type("EMAIL")
                .build();

        messageClient.sendNotification(notificationRequest);
    }

    private void sendWithdrawalNotification(
            AccountResponse account,
            TransactionRequest request) {

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .recipient(account.getEmail())
                .subject("Withdrawal Successful")
                .message(
                        "Dear " + account.getAccountHolderName() + ",\n\n" +
                                "Your withdrawal was successful.\n\n" +
                                "Amount: " + request.getAmount() + "\n" +
                                "Account Number: " + account.getAccountNumber() + "\n" +
                                "Description: " + request.getDescription() + "\n" +
                                "Remaining Balance: " + account.getBalance() + "\n\n" +
                                "Thank you for using our banking service."
                )
                .type("EMAIL")
                .build();

        messageClient.sendNotification(notificationRequest);
    }
}