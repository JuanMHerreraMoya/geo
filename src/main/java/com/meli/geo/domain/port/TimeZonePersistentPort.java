package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.TimezoneDto;

import java.util.List;

public interface TimeZonePersistentPort {
    TimezoneDto save(TimezoneDto timezoneDto);
    List<TimezoneDto> getAll();
}
