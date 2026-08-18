package com.banking.transactionservice.feign;

import com.banking.common.Core.AccountResponse;


import com.banking.common.Core.AccountUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "account-service")
public interface AccountClient {


    @PutMapping("/api/accounts/{id}/debit")
    AccountResponse debit(
            @PathVariable("id") Long id,
            @RequestParam("amount") BigDecimal amount
    );
    @PutMapping("/api/accounts/{id}/credit")
    AccountResponse credit(
            @PathVariable("id") Long id,
            @RequestParam("amount") BigDecimal amount
    );
    @GetMapping("/api/accounts/{id}")
    AccountResponse getAccountById(@PathVariable Long id);
    @PutMapping("/api/accounts/{id}")
    AccountResponse updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest accountRequest);
}
