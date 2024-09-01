package com.meli.geo.infrastructure.adapter.repository;


import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    CountryEntity findByName(String name);

    @Query(value = "SELECT name, invocation , distance FROM country WHERE distance = (SELECT MAX(distance) FROM country)", nativeQuery = true)
    Object[] getMax();

    @Query(value = "SELECT name, invocation , distance FROM country WHERE distance = (SELECT MIN(distance) FROM country)", nativeQuery = true)
    Object[] getMin();

}
