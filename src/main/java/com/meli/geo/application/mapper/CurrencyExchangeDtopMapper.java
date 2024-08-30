package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.CurrencyExchangeDto;
import com.meli.geo.infrastructure.adapter.entity.CurrencyExchangeEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyExchangeDtopMapper {
    CurrencyExchangeDto toDto(CurrencyExchangeEntity currencyExchangeEntity);
    CurrencyExchangeEntity toEntity(CurrencyExchangeDto currencyExchangeDto);
}
