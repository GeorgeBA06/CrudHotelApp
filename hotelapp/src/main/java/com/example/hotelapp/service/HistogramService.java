package com.example.hotelapp.service;

import com.example.hotelapp.entity.HistogramParameter;
import com.example.hotelapp.exception.InvalidHistogramParameterException;
import com.example.hotelapp.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistogramService {

    private final HotelRepository hotelRepository;

    public Map<String, Long> getHistogram(String param){
        HistogramParameter histogramParameter;
        try {
            histogramParameter = HistogramParameter.from(param);
        }catch (IllegalArgumentException ex){
            throw new InvalidHistogramParameterException(param);
        }

        List<Object[]> rows = switch (histogramParameter){
            case BRAND -> hotelRepository.countHotelsGroupedByBrand();
            case CITY -> hotelRepository.countHotelsGroupedByCity();
            case COUNTRY -> hotelRepository.countHotelsGroupByCountry();
            case AMENITIES -> hotelRepository.countHotelsGroupByAmenities();
        };
        return toHistogramMap(rows);
    }

    private Map<String, Long> toHistogramMap(List<Object[]> rows){
        Map<String, Long> resultMap = new LinkedHashMap<>();

        for(Object[] row : rows){
            String key = row[0] != null ? row[0].toString() : "UNKNOWN";
            Long value = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            resultMap.put(key, value);
        }

        return resultMap;
    }
}
