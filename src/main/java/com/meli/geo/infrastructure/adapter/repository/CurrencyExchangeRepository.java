package com.meli.geo.infrastructure.adapter.repository;

import com.meli.geo.infrastructure.adapter.entity.CurrencyExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchangeEntity, Long> {
    CurrencyExchangeEntity getByIso(String iso);
}
