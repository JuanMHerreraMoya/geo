package com.meli.geo.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguageDto {
    @JsonIgnore
    private Long id;
    private String name;
    private String iso;
    @JsonIgnore
    private Set<String> countryNames;

    public LanguageDto(String name, String iso, Set<String> countryToSave) {
        this.name =name;
        this.iso =iso;
        this.countryNames = countryToSave;

    }
}