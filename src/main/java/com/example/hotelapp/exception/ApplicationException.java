package com.example.hotelapp.exception;

public class ApplicationException extends RuntimeException {
    protected ApplicationException(String message) {
        super(message);
    }
}
