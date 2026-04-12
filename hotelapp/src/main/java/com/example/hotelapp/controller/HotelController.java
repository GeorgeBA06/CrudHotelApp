package com.example.hotelapp.controller;

import com.example.hotelapp.dto.request.CreateHotelRequestDto;
import com.example.hotelapp.dto.response.HotelDetailsResponseDto;
import com.example.hotelapp.dto.response.HotelShortResponseDto;
import com.example.hotelapp.service.HotelCommandService;
import com.example.hotelapp.service.HotelQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "Operations related to hotels")
public class HotelController {

    private final HotelQueryService hotelQueryService;
    private final HotelCommandService hotelCommandService;


    @Operation(summary = "Get all hotels (short view)")
    @GetMapping
    public List<HotelShortResponseDto> getAllHotels(){
        return hotelQueryService.getAllHotels();
    }

    @Operation(summary = "Get hotel details by ID")
    @GetMapping("/{id}")
    public HotelDetailsResponseDto getHotelDetailsById(@PathVariable Long id){
        return hotelQueryService.getHotelDetailsById(id);
    }

    @Operation(summary = "Create a new hotel")
    @PostMapping
    public HotelShortResponseDto createHotel(@Valid @RequestBody CreateHotelRequestDto dto){
        return hotelCommandService.createHotel(dto);
    }

    @Operation(summary = "Add amenities to hotel")
    @PostMapping("/{id}/amenities")
    public HotelDetailsResponseDto addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities
    ) {
        return hotelCommandService.addAmenities(id, amenities);
    }
}
