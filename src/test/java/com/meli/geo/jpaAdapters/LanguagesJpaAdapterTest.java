package com.meli.geo.jpaAdapters;

import com.meli.geo.application.mapper.LanguageDtoMapper;
import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.infrastructure.adapter.LanguagesJpaAdapter;
import com.meli.geo.infrastructure.adapter.entity.LanguagesEntity;
import com.meli.geo.infrastructure.adapter.repository.LanguagesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class LanguagesJpaAdapterTest {

    @InjectMocks
    private LanguagesJpaAdapter languagesJpaAdapter;

    @Mock
    private LanguagesRepository languagesRepository;

    @Mock
    private LanguageDtoMapper languageDtoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        List<LanguagesEntity> languageEntities = List.of(
                new LanguagesEntity(1L, "English", "EN", null),
                new LanguagesEntity(2L, "Spanish", "ES", null)
        );

        List<LanguageDto> languageDtos = List.of(
                new LanguageDto(1L, "English", "EN", Set.of("USA")),
                new LanguageDto(2L, "Spanish", "ES", Set.of("Spain"))
        );

        Mockito.when(languagesRepository.findAll()).thenReturn(languageEntities);
        Mockito.when(languageDtoMapper.toDto(any(LanguagesEntity.class))).thenAnswer(invocation -> {
            LanguagesEntity entity = invocation.getArgument(0);
            return new LanguageDto(entity.getId(), entity.getName(), entity.getIso(), null);
        });

        List<LanguageDto> result = languagesJpaAdapter.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("English", result.get(0).getName());
        assertEquals("Spanish", result.get(1).getName());
    }

    @Test
    public void testSave() {
        LanguageDto languageDto = new LanguageDto(1L, "French", "FR", Set.of("France"));
        LanguagesEntity languageEntity = new LanguagesEntity(1L, "French", "FR", null);

        Mockito.when(languageDtoMapper.toEntity(languageDto)).thenReturn(languageEntity);
        Mockito.when(languagesRepository.save(languageEntity)).thenReturn(languageEntity);
        Mockito.when(languageDtoMapper.toDto(languageEntity)).thenReturn(languageDto);

        LanguageDto result = languagesJpaAdapter.save(languageDto);

        assertNotNull(result);
        assertEquals("French", result.getName());
    }

    @Test
    public void testGetByName() {
        String name = "German";
        LanguagesEntity languageEntity = new LanguagesEntity(1L, "German", "DE", null);
        LanguageDto languageDto = new LanguageDto(1L, "German", "DE", Set.of("Germany"));

        Mockito.when(languagesRepository.findByName(name)).thenReturn(languageEntity);
        Mockito.when(languageDtoMapper.toDto(languageEntity)).thenReturn(languageDto);

        LanguageDto result = languagesJpaAdapter.getByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
    }
}
