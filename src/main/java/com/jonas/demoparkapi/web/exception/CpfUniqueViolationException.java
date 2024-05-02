package com.jonas.demoparkapi.web.exception;

public class CpfUniqueViolationException extends RuntimeException{
    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
