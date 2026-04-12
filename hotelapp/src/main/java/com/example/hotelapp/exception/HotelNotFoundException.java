package com.example.hotelapp.exception;

public class HotelNotFoundException extends ApplicationException {
    public HotelNotFoundException(Long hotelId) {
        super("Hotel with id: " + hotelId + "was not found");
    }
}
