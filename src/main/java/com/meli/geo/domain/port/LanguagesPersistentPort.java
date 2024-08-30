package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.domain.model.dto.UserDto;
import com.meli.geo.infrastructure.adapter.entity.LanguagesEntity;

import java.util.List;

public interface LanguagesPersistentPort {
    List<LanguageDto> getAll();
    LanguageDto save(LanguageDto languageDto);

    LanguageDto getByName(String name);
}
