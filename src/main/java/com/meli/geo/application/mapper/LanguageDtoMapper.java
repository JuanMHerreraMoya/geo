package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import com.meli.geo.infrastructure.adapter.entity.LanguagesEntity;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface LanguageDtoMapper {

    LanguageDto toDto(LanguagesEntity language);

    LanguagesEntity toEntity(LanguageDto languageDto);

    default Set<String> mapCountriesToCountryNames(Set<CountryEntity> countries) {
        return countries.stream()
                .map(CountryEntity::getName)
                .collect(Collectors.toSet());
    }

}
