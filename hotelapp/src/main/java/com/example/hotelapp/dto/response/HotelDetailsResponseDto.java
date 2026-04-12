package com.example.hotelapp.dto.response;

import java.util.List;

public record HotelDetailsResponseDto(
        Long id,
        String name,
        String description,
        String brand,
        AddressResponseDto address,
        ContactsResponseDto contacts,
        ArrivalTimeResponseDto arrivalTime,
        List<String> amenities


) {
}
