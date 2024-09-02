package com.meli.geo.infrastructure.adapter;

import com.meli.geo.application.mapper.TimeZoneDtoMapper;
import com.meli.geo.domain.model.dto.TimezoneDto;
import com.meli.geo.domain.port.TimeZonePersistentPort;
import com.meli.geo.infrastructure.adapter.repository.TimeZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TimeZoneJpaAdapter implements TimeZonePersistentPort {

    private final TimeZoneDtoMapper timeZoneDtoMapper;
    private final TimeZoneRepository timeZoneRepository;

    public TimeZoneJpaAdapter(TimeZoneDtoMapper timeZoneDtoMapper, TimeZoneRepository timeZoneRepository) {
        this.timeZoneDtoMapper = timeZoneDtoMapper;
        this.timeZoneRepository = timeZoneRepository;
    }

    @Override
    public TimezoneDto save(TimezoneDto timezoneDto) {
        return timeZoneDtoMapper.toDto(timeZoneRepository.save(timeZoneDtoMapper.toEntity(timezoneDto)));
    }

    @Override
    public List<TimezoneDto> getAll() {
        return timeZoneRepository.findAll().stream().map(timeZoneDtoMapper::toDto).toList();
    }

}
