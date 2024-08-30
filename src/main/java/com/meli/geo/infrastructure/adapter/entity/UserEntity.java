package com.meli.geo.infrastructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "UserIp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ipUser;
    private Integer distance;

    @ManyToOne
    @JoinColumn(name = "countryEntity_id")
    //@JsonBackReference
    private CountryEntity countryEntity;
}
