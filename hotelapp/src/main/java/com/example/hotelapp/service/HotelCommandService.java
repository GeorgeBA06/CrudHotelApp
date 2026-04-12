package com.example.hotelapp.service;

import com.example.hotelapp.dto.request.CreateHotelRequestDto;
import com.example.hotelapp.dto.response.HotelShortResponseDto;
import com.example.hotelapp.entity.HotelEntity;
import com.example.hotelapp.repository.HotelRepository;
import com.example.hotelapp.util.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelCommandService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelShortResponseDto createHotel(CreateHotelRequestDto dto){
        HotelEntity hotelEntity = hotelMapper.toHotelEntity(dto);
        HotelEntity newHotel = hotelRepository.save(hotelEntity);
        return hotelMapper.toShortResponse(newHotel);
    }
}
