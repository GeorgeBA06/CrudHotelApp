package com.example.hotelapp.service;

import com.example.hotelapp.dto.request.CreateHotelRequestDto;
import com.example.hotelapp.dto.response.HotelDetailsResponseDto;
import com.example.hotelapp.dto.response.HotelShortResponseDto;
import com.example.hotelapp.entity.AmenityEntity;
import com.example.hotelapp.entity.HotelEntity;
import com.example.hotelapp.exception.HotelNotFoundException;
import com.example.hotelapp.exception.InvalidAmenitiesRequestException;
import com.example.hotelapp.repository.AmenityRepository;
import com.example.hotelapp.repository.HotelRepository;
import com.example.hotelapp.util.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelCommandService {
    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    public HotelShortResponseDto createHotel(CreateHotelRequestDto dto){
        HotelEntity hotelEntity = hotelMapper.toHotelEntity(dto);
        HotelEntity newHotel = hotelRepository.save(hotelEntity);
        return hotelMapper.toShortResponse(newHotel);
    }

    public HotelDetailsResponseDto addAmenities(Long hotelId, List<String> amenities){
        Set<String> normalizedAmenities = normalizeAmenities(amenities);

        HotelEntity hotelEntity = hotelRepository.findDetailedById(hotelId)
                .orElseThrow(()-> new HotelNotFoundException(hotelId));

        for(String amenity : normalizedAmenities){
            AmenityEntity amenityEntity = amenityRepository.findByNameIgnoreCase(amenity)
                    .orElseGet(()->createAmenity(amenity));

            hotelEntity.addAmenity(amenityEntity);
        }

        HotelEntity savedHotel = hotelRepository.save(hotelEntity);
        return hotelMapper.toDetailsResponse(savedHotel);
    }

    private AmenityEntity createAmenity(String amenityName){
        AmenityEntity amenity = new AmenityEntity();
        amenity.setName(amenityName);
        return amenityRepository.save(amenity);
    }

    private Set<String> normalizeAmenities(List<String> amenities){
        if(amenities == null || amenities.isEmpty()){
            throw new InvalidAmenitiesRequestException("Amenities list must not be null");
        }

        Set<String> normalized = new LinkedHashSet<>();

        for(String amenity : amenities){
            if(amenity == null){
                continue;
            }
            String cleaned = amenity.trim().replaceAll("\\s+", " ");
            if(!cleaned.isEmpty()){
                normalized.add(cleaned);
            }
        }

        if(normalized.isEmpty()){
            throw new InvalidAmenitiesRequestException("Amenities list must contain at least one non-blank value");
        }

        return normalized;

    }
}
