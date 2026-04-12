package com.example.hotelapp.repository;


import com.example.hotelapp.entity.AmenityEntity;
import com.example.hotelapp.entity.HotelAddressEntity;
import com.example.hotelapp.entity.HotelEntity;
import com.example.hotelapp.service.model.HotelSearchCriteria;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public final class HotelSpecification {
    private HotelSpecification(){
    }

    public static Specification<HotelEntity> byCriteria(HotelSearchCriteria criteria){
        return ((root, query, cb) -> {
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            if(hasText(criteria.name())){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("name")),
                                "%" + criteria.name().trim().toLowerCase() + "%"
                        )
                );
            }

            if (hasText(criteria.brand())) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("brand")),
                                "%" + criteria.brand().trim().toLowerCase() + "%"
                        )
                );
            }

            if (hasText(criteria.city())) {
                Join<HotelEntity, HotelAddressEntity> addressJoin = root.join("address", JoinType.INNER);
                predicates.add(
                        cb.like(
                                cb.lower(addressJoin.get("city")),
                                "%" + criteria.city().trim().toLowerCase() + "%"
                        )
                );
            }

            if (hasText(criteria.country())) {
                Join<HotelEntity, HotelAddressEntity> addressJoin = root.join("address", JoinType.INNER);
                predicates.add(
                        cb.like(
                                cb.lower(addressJoin.get("country")),
                                "%" + criteria.country().trim().toLowerCase() + "%"
                        )
                );
            }

            List<String> normalizedAmenities = normalizeAmenities(criteria.amenities());
            if (!normalizedAmenities.isEmpty()) {
                Join<HotelEntity, AmenityEntity> amenityJoin = root.join("amenities", JoinType.LEFT);
                predicates.add(
                        cb.lower(amenityJoin.get("name")).in(normalizedAmenities)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));

        });

    }

    private static List<String> normalizeAmenities(List<String> amenities) {
        if (amenities == null || amenities.isEmpty()) {
            return List.of();
        }

        return amenities.stream()
                .filter(value -> value != null && !value.isBlank())
                .map(String::trim)
                .map(value -> value.replaceAll("\\s+", " "))
                .map(String::toLowerCase)
                .distinct()
                .toList();
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
