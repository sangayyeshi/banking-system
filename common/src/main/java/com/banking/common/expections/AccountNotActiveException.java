package com.banking.common.expections;

public class AccountNotActiveException extends BusinessException {
    public AccountNotActiveException(String message) {
        super(message);
    }
}
