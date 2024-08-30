package com.meli.geo.infrastructure.adapter.repository;

import com.meli.geo.domain.model.dto.CurrencyExchangeDto;
import com.meli.geo.infrastructure.adapter.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Long> {
    CurrencyExchangeEntity getByIso(String iso);
}
