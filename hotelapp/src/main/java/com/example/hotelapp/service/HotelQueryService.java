package com.example.hotelapp.service;

import com.example.hotelapp.dto.response.HotelDetailsResponseDto;
import com.example.hotelapp.dto.response.HotelShortResponseDto;
import com.example.hotelapp.exception.HotelNotFoundException;
import com.example.hotelapp.repository.HotelRepository;
import com.example.hotelapp.util.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelQueryService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<HotelShortResponseDto> getAllHotels(){
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }

    public HotelDetailsResponseDto getHotelDetailsById(Long id){
        return hotelRepository.findDetailedById(id).stream()
                .map(hotelMapper::toDetailsResponse)
                .findAny().orElseThrow(()->new HotelNotFoundException(id));
    }
}
