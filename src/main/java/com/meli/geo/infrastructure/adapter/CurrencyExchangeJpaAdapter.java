package com.meli.geo.infrastructure.adapter;

import com.meli.geo.application.mapper.CurrencyExchangeDtopMapper;
import com.meli.geo.domain.model.dto.CurrencyExchangeDto;
import com.meli.geo.domain.port.CurrencyExchangePersistentPort;
import com.meli.geo.infrastructure.adapter.repository.CurrencyExchangeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CurrencyExchangeJpaAdapter implements CurrencyExchangePersistentPort {
    private final CurrencyExchangeRepository currencyExchangeRepository;
    private final CurrencyExchangeDtopMapper currencyExchangeDtopMapper;

    public CurrencyExchangeJpaAdapter(CurrencyExchangeRepository currencyExchangeRepository, CurrencyExchangeDtopMapper currencyExchangeDtopMapper) {
        this.currencyExchangeRepository = currencyExchangeRepository;
        this.currencyExchangeDtopMapper = currencyExchangeDtopMapper;
    }

    @Override
    public void save(CurrencyExchangeDto exchangeDto) {
        currencyExchangeRepository.save(currencyExchangeDtopMapper.toEntity(exchangeDto));
    }

    @Override
    public CurrencyExchangeDto getByIso(String iso) {
        return currencyExchangeDtopMapper.toDto(currencyExchangeRepository.getByIso(iso));
    }

    @Override
    public List<CurrencyExchangeDto> getAll() {
        return currencyExchangeRepository.findAll().stream().map(currencyExchangeDtopMapper::toDto).toList();
    }
}
