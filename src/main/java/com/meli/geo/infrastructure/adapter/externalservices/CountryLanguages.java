package com.meli.geo.infrastructure.adapter.externalservices;

import com.meli.geo.application.mapper.RestCountryDtoMapper;
import com.meli.geo.domain.model.constant.CountryLanguagesConstant;
import com.meli.geo.domain.model.dto.RestCountriesDto;
import com.meli.geo.domain.port.CountryLanguagesPort;
import com.meli.geo.infrastructure.adapter.entity.RestCountriesEntity;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class CountryLanguages implements CountryLanguagesPort {
    private final RestCountryDtoMapper countryDtoMapper;

    public CountryLanguages(RestCountryDtoMapper countryDtoMapper) {
        this.countryDtoMapper = countryDtoMapper;
    }

    public RestCountriesDto getLanguagesByCountry(String countryIso) {
        try {
            URL url = new URL(CountryLanguagesConstant.URL + countryIso);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.next());
            }
            scanner.close();

            JSONObject countryData = new JSONObject(jsonResponse.substring(1,jsonResponse.toString().length()-1));
            JSONObject languages = countryData.getJSONObject("languages");
            Map<String, Object> languagesList = languages.toMap();
            List<Object> timeZoneList = countryData.getJSONArray("timezones").toList();
            return  countryDtoMapper.toDto(new RestCountriesEntity(languagesList,timeZoneList));
        } catch (Exception e) {
            throw new GeoException(HttpStatus.BAD_REQUEST,"Error buscando lenguajes");
        }
    }
}


