package com.example.hotelapp.repository;

import com.example.hotelapp.entity.HotelEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface HotelRepository extends JpaRepository<HotelEntity, Long>, JpaSpecificationExecutor<HotelEntity> {

    @Override
    @EntityGraph(attributePaths = {"contacts", "address"})
    List<HotelEntity> findAll();

    @EntityGraph(attributePaths = {"contacts", "address", "arrivalTime", "amenities"})
    Optional<HotelEntity> findDetailedById(Long id);

    @Query("""
select h.brand, count(h.id)
from HotelEntity h 
group by h.brand
order by h.brand
""")
    List<Object[]> countHotelsGroupedByBrand();

    @Query("""
select a.city, count(h.id)
from HotelEntity h
join h.address a
group by a.city
order by a.city

""")
    List<Object[]> countHotelsGroupedByCity();

    @Query("""
select a.country, count(h.id)
from HotelEntity h 
join h.address a
group by a.country
order by a.country
""")
    List<Object[]> countHotelsGroupByCountry();

    @Query("""
select am.name, count(h.id)
from HotelEntity h
join h.amenities am
group by am.name
order by am.name
""")
    List<Object[]> countHotelsGroupByAmenities();
}

