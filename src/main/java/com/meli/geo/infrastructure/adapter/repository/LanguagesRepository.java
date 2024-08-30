package com.meli.geo.infrastructure.adapter.repository;

import com.meli.geo.infrastructure.adapter.entity.LanguagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguagesRepository extends JpaRepository<LanguagesEntity, Long> {
    LanguagesEntity findByName(String name);
}