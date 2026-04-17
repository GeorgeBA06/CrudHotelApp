package com.example.hotelapp.dto.response;

import java.time.LocalTime;

public record ArrivalTimeResponseDto(
        LocalTime checkIn,
        LocalTime checkOut
) {
}
