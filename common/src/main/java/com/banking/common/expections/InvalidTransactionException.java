package com.banking.common.expections;

public class InvalidTransactionException extends BusinessException {
    public InvalidTransactionException(String message) {
        super(message);
    }
}
