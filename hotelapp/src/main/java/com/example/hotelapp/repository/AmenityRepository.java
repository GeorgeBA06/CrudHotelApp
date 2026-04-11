package com.example.hotelapp.repository;

import com.example.hotelapp.entity.AmenityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface AmenityRepository extends JpaRepository<AmenityEntity, Long>{
    Optional<AmenityEntity> findByNameIgnoreCase(String name);

    @Query("""
           select a
           from AmenityEntity a
           where lower(a.name) in :names
           """)
    List<AmenityEntity> findAllByNamesIgnoreCase(Collection<String> names);
}
