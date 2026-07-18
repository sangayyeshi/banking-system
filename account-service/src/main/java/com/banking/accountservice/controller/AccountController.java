package com.banking.accountservice.controller;

import com.banking.accountservice.dto.AccountRequest;
import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    //create account
    @PostMapping
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }
    @GetMapping("/all")
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @GetMapping("/{id}")
    public AccountResponse getAccountById(@Valid @PathVariable("id") Long accountId) {
        return accountService.getAccountById(accountId);
    }
    @GetMapping("/number/{accountNumber}")
     public AccountResponse getAccountByAccountNumber(@Valid @PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountByAccountNumber(accountNumber);

    }   @DeleteMapping("/{id}")
       public void deleteAccountById(@Valid @PathVariable("id") Long accountId) {
        accountService.deleteAccountById(accountId);
    }


}
