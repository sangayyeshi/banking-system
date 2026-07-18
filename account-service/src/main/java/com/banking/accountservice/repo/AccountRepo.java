package com.banking.accountservice.repo;

import com.banking.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
     Optional<Account> findByaccountNumber(String accountNumber);
     Optional<Account> findByemail(String email);

}
