package com.jonas.demoparkapi.exception;

public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException(String message) {
        super(message);
    }
}
