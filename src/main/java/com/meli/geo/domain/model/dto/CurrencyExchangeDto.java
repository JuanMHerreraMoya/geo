package com.meli.geo.domain.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrencyExchangeDto {
    private Long id;
    private String iso;
    private String usdPrice;
    private Date lastUpdate;

    public CurrencyExchangeDto(String iso, String usdPrice, Date date) {
        this.iso = iso;
        this.usdPrice = usdPrice;
        this.lastUpdate = date;
    }
}
