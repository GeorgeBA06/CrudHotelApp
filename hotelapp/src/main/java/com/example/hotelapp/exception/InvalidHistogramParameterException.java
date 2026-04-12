package com.example.hotelapp.exception;

public class InvalidHistogramParameterException extends ApplicationException {
    public InvalidHistogramParameterException(String param) {
        super("Unsupported histogram parameter: " + param);
    }
}
