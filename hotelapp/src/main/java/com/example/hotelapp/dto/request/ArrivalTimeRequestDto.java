package com.example.hotelapp.dto.request;

import jakarta.validation.constraints.NotNull;


import java.time.LocalTime;

public record ArrivalTimeRequestDto(
        @NotNull
        LocalTime checkIn,

        LocalTime checkOut
) {
}
