package com.banking.common.expections;

public class InvalidAmountException extends BusinessException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
