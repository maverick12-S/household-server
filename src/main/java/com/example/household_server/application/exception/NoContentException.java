package com.example.household_server.application.exception;

public class NoContentException extends RuntimeException {
    public NoContentException(String message){
        super(message);
    }
}
