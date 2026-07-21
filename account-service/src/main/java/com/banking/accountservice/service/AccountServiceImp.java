package com.banking.accountservice.service;

import com.banking.accountservice.dto.AccountRequest;
import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.entity.Account;
import com.banking.common.enumrate.AccountStatus;
import com.banking.accountservice.repo.AccountRepo;
import com.banking.common.Core.AccountUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class AccountServiceImp  implements AccountService {
    private final AccountRepo accountRepo;
    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {

        Account account = Account.builder()
                .accountNumber(UUID.randomUUID().toString().substring(0,10))
                .accountHolderName(accountRequest.getAccountHolderName())
                .email(accountRequest.getEmail())
                .phoneNumber(accountRequest.getPhoneNumber())
                .balance(accountRequest.getBalance())
                .accountStatus(AccountStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepo.save(account);

        return map(savedAccount);
    }


    private AccountResponse map(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountHolderName(account.getAccountHolderName())
                .email(account.getEmail())
                .phoneNumber(account.getPhoneNumber())
                .status(account.getAccountStatus())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .build();
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return accountRepo.findAll()
                .stream()
                .map(this::map).toList();
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        return map(accountRepo.findById(id).orElseThrow());
    }

    @Override
    public AccountResponse getAccountByEmail(String email) {
        return map(accountRepo.findByemail(email).orElseThrow());
    }

    @Override
    public AccountResponse getAccountByAccountNumber(String accountNumber) {
        return map(accountRepo.findByaccountNumber(accountNumber).orElseThrow());
    }

    @Override
    public void deleteAccountById(Long id) {
       accountRepo.deleteById(id);
    }

    @Override
    public AccountResponse updateAccount(Long id, AccountUpdateRequest accountRequest) {
        Account account = accountRepo.findById(id).orElseThrow(
                ()-> new RuntimeException("account not found"));
        account.setBalance(accountRequest.getAmount());
        Account updatedAccount = accountRepo.save(account);
        return map(updatedAccount);
    }


}
