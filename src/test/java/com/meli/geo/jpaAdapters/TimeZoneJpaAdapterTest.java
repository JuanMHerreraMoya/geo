package com.meli.geo.jpaAdapters;

import com.meli.geo.application.mapper.TimeZoneDtoMapper;
import com.meli.geo.domain.model.dto.TimezoneDto;
import com.meli.geo.infrastructure.adapter.TimeZoneJpaAdapter;
import com.meli.geo.infrastructure.adapter.entity.TimeZoneEntity;
import com.meli.geo.infrastructure.adapter.repository.TimeZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class TimeZoneJpaAdapterTest {

    @InjectMocks
    private TimeZoneJpaAdapter timeZoneJpaAdapter;

    @Mock
    private TimeZoneDtoMapper timeZoneDtoMapper;

    @Mock
    private TimeZoneRepository timeZoneRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        TimezoneDto timezoneDto = new TimezoneDto(1L, "UTC", new Date().toString(), new Date(), null);
        TimeZoneEntity timezoneEntity = new TimeZoneEntity(1L, "UTC",new Date().toString(), new Date(), null);

        Mockito.when(timeZoneDtoMapper.toEntity(timezoneDto)).thenReturn(timezoneEntity);
        Mockito.when(timeZoneRepository.save(timezoneEntity)).thenReturn(timezoneEntity);
        Mockito.when(timeZoneDtoMapper.toDto(timezoneEntity)).thenReturn(timezoneDto);

        TimezoneDto result = timeZoneJpaAdapter.save(timezoneDto);

        assertNotNull(result);
        assertEquals("UTC", result.getUtc());
    }

    @Test
    public void testGetAll() {
        List<TimeZoneEntity> timezoneEntities = List.of(
                new TimeZoneEntity(1L, "UTC+1", new Date().toString(), new Date(), null),
                new TimeZoneEntity(2L, "UTC-5", new Date().toString(), new Date(), null)
        );

        List<TimezoneDto> timezoneDtos = List.of(
                new TimezoneDto(1L, "UTC+1", new Date().toString(), new Date(), null),
                new TimezoneDto(2L, "UTC-5", new Date().toString(), new Date(),  null)
        );

        Mockito.when(timeZoneRepository.findAll()).thenReturn(timezoneEntities);
        Mockito.when(timeZoneDtoMapper.toDto(any(TimeZoneEntity.class))).thenAnswer(invocation -> {
            TimeZoneEntity entity = invocation.getArgument(0);
            return new TimezoneDto(entity.getId(), entity.getUtc(), new Date().toString(), new Date(),null);
        });

        List<TimezoneDto> result = timeZoneJpaAdapter.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("UTC+1", result.get(0).getUtc());
        assertEquals("UTC-5", result.get(1).getUtc());
    }
}
