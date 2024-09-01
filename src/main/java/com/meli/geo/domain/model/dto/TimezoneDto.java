package com.meli.geo.domain.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimezoneDto {
    @JsonIgnore
    private Long id;
    private String utc;
    private Date utcDate;
    private Date lastUpdate;
    @JsonIgnore
    private Set<String> countryNames;

    public TimezoneDto(String utc, Set<String> countryNames, Date utcDate, Date lastUpdate){
        this.countryNames = countryNames;
        this.utc = utc;
        this.utcDate = utcDate;
        this.lastUpdate = lastUpdate;
    }
}
