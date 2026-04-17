package com.example.hotelapp.entity;

import com.example.hotelapp.exception.InvalidHistogramParameterException;

public enum HistogramParameter {
    BRAND,
    CITY,
    COUNTRY,
    AMENITIES;

    public static HistogramParameter from(String value){
        if(value == null || value.isEmpty()){
            throw new InvalidHistogramParameterException("Histogram parameter must not be blank");
        }

        return switch (value.trim().toLowerCase()){
            case "brand" -> BRAND;
            case "city" -> CITY;
            case "country" -> COUNTRY;
            case "amenities" -> AMENITIES;
            default -> throw new IllegalArgumentException("Unsupported histogram parameter: " + value);
        };
    }
}
