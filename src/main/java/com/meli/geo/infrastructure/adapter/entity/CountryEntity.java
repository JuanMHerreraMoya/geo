package com.meli.geo.infrastructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name="Country")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String iso;
    private Integer distance;
    private Integer invocation;
    private String currency;

    @OneToMany(mappedBy = "countryEntity")
    @JsonManagedReference
    private Set<UserEntity> userEntities;

    @ManyToMany
    @JoinTable(
            name = "country_language", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "country_id"), // Clave foránea hacia la tabla Country
            inverseJoinColumns = @JoinColumn(name = "language_id") // Clave foránea hacia la tabla Language
    )
    private Set<LanguagesEntity> languages;
}
