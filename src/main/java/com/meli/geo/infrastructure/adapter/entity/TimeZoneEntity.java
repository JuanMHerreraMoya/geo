package com.meli.geo.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
@Entity(name = "TimeZone")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TimeZoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String utc;
    private String utcDate;
    private Date lastUpdate;
    @ManyToMany(mappedBy = "timeZone",cascade = CascadeType.ALL)
    private Set<CountryEntity> countries;
}
