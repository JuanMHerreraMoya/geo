package com.meli.geo.infrastructure.adapter.repository;


import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    CountryEntity findByName(String name);
}
