package com.bichofull.backend.exception;

public class InsufficientBalanceException extends RuntimeException {
    
    // Construtor padrão
    public InsufficientBalanceException() {
        super("Saldo insuficiente para realizar a operação");
    }
    
    public InsufficientBalanceException(String message) {
        super(message);  
    }
    
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}