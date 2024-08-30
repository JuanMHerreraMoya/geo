package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.RestCountriesDto;

public interface CountryLanguagesPort {
    RestCountriesDto getLanguagesByCountry(String countryIso);
}
