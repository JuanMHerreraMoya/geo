package com.meli.geo.jpaAdapters;

import com.meli.geo.application.mapper.CurrencyExchangeDtopMapper;
import com.meli.geo.domain.model.dto.CurrencyExchangeDto;
import com.meli.geo.infrastructure.adapter.CurrencyExchangeJpaAdapter;
import com.meli.geo.infrastructure.adapter.entity.CurrencyExchangeEntity;
import com.meli.geo.infrastructure.adapter.repository.CurrencyExchangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class CurrencyExchangeJpaAdapterTest {

    @InjectMocks
    private CurrencyExchangeJpaAdapter currencyExchangeJpaAdapter;

    @Mock
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Mock
    private CurrencyExchangeDtopMapper currencyExchangeDtopMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        CurrencyExchangeDto exchangeDto = new CurrencyExchangeDto(1L,"USD", "1.0", new Date());
        CurrencyExchangeEntity exchangeEntity = new CurrencyExchangeEntity(1L,"USD", "1.0", new Date());

        Mockito.when(currencyExchangeDtopMapper.toEntity(exchangeDto)).thenReturn(exchangeEntity);

        currencyExchangeJpaAdapter.save(exchangeDto);

        Mockito.verify(currencyExchangeRepository, Mockito.times(1)).save(exchangeEntity);
    }

    @Test
    public void testGetByIso() {
        String iso = "USD";
        CurrencyExchangeEntity exchangeEntity = new CurrencyExchangeEntity(1L,"USD", "1.0", new Date());
        CurrencyExchangeDto exchangeDto = new CurrencyExchangeDto(1L,"USD", "1.0", new Date());

        Mockito.when(currencyExchangeRepository.getByIso(iso)).thenReturn(exchangeEntity);
        Mockito.when(currencyExchangeDtopMapper.toDto(exchangeEntity)).thenReturn(exchangeDto);

        CurrencyExchangeDto result = currencyExchangeJpaAdapter.getByIso(iso);

        assertNotNull(result);
        assertEquals(iso, result.getIso());
    }

    @Test
    public void testGetAll() {
        List<CurrencyExchangeEntity> exchangeEntities = List.of(
                new CurrencyExchangeEntity(1L,"USD", "1.0", new Date()),
                new CurrencyExchangeEntity(1L,"EUR", "0.85", new Date())
        );

        List<CurrencyExchangeDto> exchangeDtos = List.of(
                new CurrencyExchangeDto("USD", "1.0", new Date()),
                new CurrencyExchangeDto("EUR", "0.85", new Date())
        );

        Mockito.when(currencyExchangeRepository.findAll()).thenReturn(exchangeEntities);
        Mockito.when(currencyExchangeDtopMapper.toDto(any(CurrencyExchangeEntity.class))).thenAnswer(invocation -> {
            CurrencyExchangeEntity entity = invocation.getArgument(0);
            return new CurrencyExchangeDto(entity.getIso(), entity.getUsdPrice(), entity.getLastUpdate());
        });

        List<CurrencyExchangeDto> result = currencyExchangeJpaAdapter.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("USD", result.get(0).getIso());
        assertEquals("EUR", result.get(1).getIso());
    }
}
