package com.devsu.hackerearth.backend.account.infrastructure.exception;

public class DontDeleteTransactionsException extends RuntimeException {
    public DontDeleteTransactionsException(String message) {
        super(message);
    }
}
