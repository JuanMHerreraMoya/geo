package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.CountryDto;

public interface CountryPersistencePort {
    CountryDto getByCountry(String country);
    void save(CountryDto countryToSave);
}
