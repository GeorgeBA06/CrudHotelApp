package com.example.hotelapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequestDto(
        @NotNull
        Integer houseNumber,

        @NotBlank
        String street,

        @NotBlank
        String city,

        @NotBlank
        String country,

        @NotBlank
        String postCode
) {
}
