package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface CountryDtoMapper {

    //CountryDtoMapper INSTANCE = Mappers.getMapper(CountryDtoMapper.class);

    @Mapping(target = "usdExchange", ignore = true)
    @Mapping(target = "timeList", ignore = true)
    @Mapping(source = "languages", target = "languageNames")
    CountryDto toDto(CountryEntity countryEntity);

    @Mapping(source = "languageNames", target = "languages")
    CountryEntity toEntity(CountryDto countryDto);
}

