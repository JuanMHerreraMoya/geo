package com.meli.geo.application.usecases;

import com.meli.geo.domain.model.dto.CountryDto;

public interface GeoUseCase {


    CountryDto getByIp(String ip);
}
