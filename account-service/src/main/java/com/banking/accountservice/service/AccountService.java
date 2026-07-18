package com.banking.accountservice.service;


import com.banking.accountservice.dto.AccountRequest;
import com.banking.accountservice.dto.AccountResponse;
import com.banking.accountservice.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountService {
    // create the method signature
     AccountResponse createAccount(AccountRequest accountRequest);
     List<AccountResponse> getAllAccounts();
     AccountResponse getAccountById(Long id);
     AccountResponse getAccountByEmail(String email);
     AccountResponse getAccountByAccountNumber(String accountNumber);
     void deleteAccountById(Long id);


}
