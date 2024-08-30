package com.meli.geo.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDto {
    @JsonIgnore
    private Long id;
    private String name;
    private Integer distance;
    private String iso;
    private Integer invocation;
    private String currency;
    private String usdExchange;
    private Set<LanguageDto> languageNames;
    private List<String> timeList;

    public CountryDto(String country, String countryCode, Integer distance, int invocation, String currency,Set<LanguageDto>languageNames) {
        this.name = country;
        this.iso = countryCode;
        this.distance = distance;
        this.invocation = invocation;
        this.currency = currency;
        this.languageNames = languageNames;
    }
}
