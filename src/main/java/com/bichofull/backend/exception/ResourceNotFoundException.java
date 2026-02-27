package com.bichofull.backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s não encontrado com id: %d", resource, id));
    }
}