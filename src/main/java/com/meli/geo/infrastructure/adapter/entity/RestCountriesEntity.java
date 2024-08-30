package com.meli.geo.infrastructure.adapter.entity;


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
public class RestCountriesEntity {
    private Map<String,Object> lenguages;
    private List<Object> timeZones;
}
