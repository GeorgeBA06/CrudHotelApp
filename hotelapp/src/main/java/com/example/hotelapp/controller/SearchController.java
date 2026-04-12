package com.example.hotelapp.controller;

import com.example.hotelapp.dto.response.HotelShortResponseDto;
import com.example.hotelapp.service.SearchService;
import com.example.hotelapp.service.model.HotelSearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Hotel search operations")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "Search hotels by filters")
    @GetMapping
    public List<HotelShortResponseDto> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities
    ){
        HotelSearchCriteria criteria = new HotelSearchCriteria(
                name,
                brand,
                city,
                country,
                amenities
        );
        return searchService.search(criteria);
    }
}
