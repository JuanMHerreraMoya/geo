package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.LanguageDto;

import java.util.List;

public interface LanguagesPersistentPort {
    List<LanguageDto> getAll();
    LanguageDto save(LanguageDto languageDto);
    LanguageDto getByName(String name);
}
