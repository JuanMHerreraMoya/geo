package com.meli.geo.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "Languages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LanguagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String iso;

    @ManyToMany(mappedBy = "languages",cascade = CascadeType.ALL)
    private Set<CountryEntity> countries;


}
