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
    private CountryDto country;

    public UserDto(String ip, Integer distance, CountryDto response) {
        this.ipUser = ip;
        this.distance = distance;
        this.country = response;
    }
}
