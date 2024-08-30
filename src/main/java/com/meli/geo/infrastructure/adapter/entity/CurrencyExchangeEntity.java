package com.meli.geo.infrastructure.adapter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name="CurrencyExchange")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrencyExchangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String iso;
    private String usdPrice;
    private Date lastUpdate;
}
