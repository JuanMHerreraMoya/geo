package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.CountryDto;

import java.util.List;

public interface CountryPersistencePort {
    CountryDto getByCountry(String country);
    CountryDto save(CountryDto countryToSave);
    List<CountryDto> getAll();

    Object[] getMax();

    Object[] getMin();
}
