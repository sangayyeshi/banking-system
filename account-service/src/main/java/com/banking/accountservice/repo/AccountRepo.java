package com.banking.accountservice.repo;

import com.banking.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
     Optional<Account> findByaccountNumber(String accountNumber);
     Optional<Account> findByemail(String email);

     // this happens inside the database no to overwrite  the same amount
     @Modifying
     @Query("""
    UPDATE Account a
    SET a.balance = a.balance - :amount
    WHERE a.id = :id
    AND a.accountStatus = com.banking.common.enumrate.AccountStatus.ACTIVE
    AND a.balance >= :amount
""")
     int debit(
             @Param("id") Long id,
             @Param("amount") BigDecimal amount
     );

     @Modifying
     @Query("""
    UPDATE Account a
    SET a.balance = a.balance + :amount
    WHERE a.id = :id
    AND a.accountStatus = com.banking.common.enumrate.AccountStatus.ACTIVE
""")
     int credit(
             @Param("id") Long id,
             @Param("amount") BigDecimal amount
     );
}


