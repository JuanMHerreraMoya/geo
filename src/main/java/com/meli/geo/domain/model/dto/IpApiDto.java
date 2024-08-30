package com.meli.geo.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IpApiDto {
    private String country;
    private String countryCode;
    private double lat;
    private double lon;
    private int offset;
    private String currency;
}
