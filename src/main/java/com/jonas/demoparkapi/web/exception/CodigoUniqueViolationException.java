package com.jonas.demoparkapi.web.exception;

public class CodigoUniqueViolationException extends RuntimeException{
    public CodigoUniqueViolationException(String message){
        super(message);
    }

}
