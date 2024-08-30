package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.CurrencyExchangeDto;

import java.util.List;

public interface CurrencyExchangePersistentPort {
    void save(CurrencyExchangeDto exchangeDtos);

    CurrencyExchangeDto getByIso(String iso);
    List<CurrencyExchangeDto> getAll();

}
