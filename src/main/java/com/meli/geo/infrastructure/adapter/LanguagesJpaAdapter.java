package com.meli.geo.infrastructure.adapter;


import com.meli.geo.application.mapper.LanguageDtoMapper;
import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.domain.port.LanguagesPersistentPort;
import com.meli.geo.infrastructure.adapter.repository.LanguagesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LanguagesJpaAdapter implements LanguagesPersistentPort {

    private final LanguagesRepository languagesRepository;
    private final LanguageDtoMapper languageDtoMapper;

    public LanguagesJpaAdapter(LanguagesRepository languagesRepository, LanguageDtoMapper languageDtoMapper) {
        this.languagesRepository = languagesRepository;
        this.languageDtoMapper = languageDtoMapper;
    }

    @Override
    public List<LanguageDto> getAll() {
        return languagesRepository.findAll().stream().map(languageDtoMapper::toDto).toList();
    }

    @Override
    public LanguageDto save(LanguageDto languageDto) {
        return languageDtoMapper.toDto(languagesRepository.save(languageDtoMapper.toEntity(languageDto)));
    }

    @Override
    public LanguageDto getByName(String name) {
        return languageDtoMapper.toDto(languagesRepository.findByName(name));
    }
}
