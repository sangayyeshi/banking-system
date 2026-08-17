package com.banking.common.expections;

public class TransactionFailedException extends BusinessException {
    public TransactionFailedException(String message) {
        super(message);
    }
    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
