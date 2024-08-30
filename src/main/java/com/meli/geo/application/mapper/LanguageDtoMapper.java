package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import com.meli.geo.infrastructure.adapter.entity.LanguagesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface LanguageDtoMapper {

    //LanguageDtoMapper INSTANCE = Mappers.getMapper(LanguageDtoMapper.class);

    //@Mapping(source = "countries", target = "countryNames")
    LanguageDto toDto(LanguagesEntity language);

    //@Mapping(source = "countryNames", target = "countries")
    LanguagesEntity toEntity(LanguageDto languageDto);

    default Set<String> mapCountriesToCountryNames(Set<CountryEntity> countries) {
        return countries.stream()
                .map(CountryEntity::getName)
                .collect(Collectors.toSet());
    }

}
