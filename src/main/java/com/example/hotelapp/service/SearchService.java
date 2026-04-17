package com.example.hotelapp.service;

import com.example.hotelapp.dto.response.HotelShortResponseDto;
import com.example.hotelapp.repository.HotelRepository;
import com.example.hotelapp.repository.HotelSpecification;
import com.example.hotelapp.service.model.HotelSearchCriteria;
import com.example.hotelapp.util.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public List<HotelShortResponseDto> search(HotelSearchCriteria searchCriteria){
        return hotelRepository.findAll(HotelSpecification.byCriteria(searchCriteria)).stream()
                .map(hotelMapper ::toShortResponse)
                .toList();
    }
}
