package com.banking.transactionservice.feign;

import com.banking.common.Core.AccountResponse;


import com.banking.common.Core.AccountUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/api/accounts/{id}")
    AccountResponse getAccountById(@PathVariable Long id);
    @PutMapping("/api/accounts/{id}")
    AccountResponse updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest accountRequest);
}
