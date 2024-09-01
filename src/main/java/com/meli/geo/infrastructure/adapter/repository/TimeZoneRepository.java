package com.meli.geo.infrastructure.adapter.repository;

import com.meli.geo.infrastructure.adapter.entity.TimeZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeZoneRepository extends JpaRepository<TimeZoneEntity, Long> {
    //List<TimezoneEntity> findAllBy();
}
