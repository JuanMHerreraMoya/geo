package com.meli.geo.domain.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String ipUser;
    private Integer distance;
    private Long countryId;
    private CountryDto countryName;
}
