package com.meli.geo.service;


import com.meli.geo.application.service.DistanceService;
import com.meli.geo.domain.model.constant.CountryConstant;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.DistanceResponse;
import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.domain.model.dto.TimezoneDto;
import com.meli.geo.domain.port.CountryPersistencePort;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DistanceServiceTest {

    @InjectMocks
    private DistanceService distanceService;

    @Mock
    private CountryPersistencePort countryPersistencePort;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetMetrics() throws GeoException {
        // Arrange
        CountryDto expectedCountryDto = new CountryDto(1L,"Colombia",1000, "CO",1,"COP","4080",
                Set.of(new LanguageDto(1L,"Esp","Esp",null)),
                Set.of(new TimezoneDto(1L,"COP","4080",new Date(),null)));

        List<CountryDto> countryDtoList = List.of(expectedCountryDto);

        when(countryPersistencePort.getAll()).thenReturn(countryDtoList);
        when(countryPersistencePort.getMin()).thenReturn(new Object[]{new Object[]{"Colombia", 2, 1000}});
        when(countryPersistencePort.getMax()).thenReturn(new Object[]{new Object[]{"Argentina", 3, 2000}});

        // Act
        List<DistanceResponse> responses = distanceService.getMetrics();

        // Assert
        assertEquals(3, responses.size());

        DistanceResponse avgResponse = responses.get(2);
        assertEquals("Promedio de distancia", avgResponse.getTitulo());
        assertEquals("todos", avgResponse.getPais());
        assertEquals(1000L, avgResponse.getDistancia());
        assertEquals(1, avgResponse.getInvocaciones());

        DistanceResponse maxResponse = responses.get(1);
        assertEquals("Distancia Maxima", maxResponse.getTitulo());
        assertEquals("Argentina", maxResponse.getPais());
        assertEquals(2000L, maxResponse.getDistancia());
        assertEquals(3, maxResponse.getInvocaciones());

        DistanceResponse minResponse = responses.get(0);
        assertEquals("Distancia Minima", minResponse.getTitulo());
        assertEquals("Colombia", minResponse.getPais());
        assertEquals(1000L, minResponse.getDistancia());
        assertEquals(2, minResponse.getInvocaciones());
    }

    @Test
    public void testGetMetrics_NoData() {
        // Arrange
        when(countryPersistencePort.getAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        GeoException thrown = assertThrows(GeoException.class, () -> distanceService.getMetrics());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals(CountryConstant.ERROR_NO_DATA, thrown.getMessage());
    }
}
