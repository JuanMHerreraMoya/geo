package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.TimezoneDto;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import com.meli.geo.infrastructure.adapter.entity.TimeZoneEntity;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface TimeZoneDtoMapper {
    TimezoneDto toDto(TimeZoneEntity timezoneEntity);

    TimeZoneEntity toEntity(TimezoneDto timezoneDto);

    default Set<String> mapCountriesToCountryNames(Set<CountryEntity> countries) {
        return countries.stream()
                .map(CountryEntity::getName)
                .collect(Collectors.toSet());
    }
}
