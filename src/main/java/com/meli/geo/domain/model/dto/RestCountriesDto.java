package com.meli.geo.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestCountriesDto {
    private Map<String,Object> lenguages;
    private List<Object> timeZones;

}
