package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.RestCountriesDto;
import com.meli.geo.infrastructure.adapter.entity.RestCountriesEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RestCountryDtoMapper {

    RestCountriesDto toDto(RestCountriesEntity restCountriesEntity);
    RestCountriesEntity toEntity(RestCountriesDto restCountriesDto);
}
