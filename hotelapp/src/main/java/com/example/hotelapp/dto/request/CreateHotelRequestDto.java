package com.example.hotelapp.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateHotelRequestDto(
        @NotBlank
        String name,

        String description,

        @NotBlank
        String brand,

        @NotNull
        @Valid
        AddressRequestDto addressRequestDto,

        @NotNull
        @Valid
        ContactsRequestDto contactsRequestDto,

        @NotNull
        @Valid
        ArrivalTimeRequestDto arrivalTimeRequestDto

) {
}
