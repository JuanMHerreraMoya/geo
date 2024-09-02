package com.meli.geo.rest;

import com.meli.geo.application.service.DistanceService;
import com.meli.geo.application.service.GeoService;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.DistanceResponse;
import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.domain.model.dto.TimezoneDto;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import com.meli.geo.infrastructure.rest.controller.GeoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GeoControllerTest {

    @InjectMocks
    private GeoController geoController;

    @Mock
    private GeoService geoService;

    @Mock
    private DistanceService distanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDataByIp() throws GeoException {
        // Arrange
        String ip = "192.168.1.1";
        CountryDto expectedCountryDto = new CountryDto(1L,"Colombia",1000, "CO",1,"COP","4080",
                Set.of(new LanguageDto(1L,"Esp","Esp",null)),
                Set.of(new TimezoneDto(1L,"COP","4080",new Date(),null)));
        when(geoService.getByIp(ip)).thenReturn(expectedCountryDto);

        // Act
        CountryDto actualCountryDto = geoController.getDataByIp(ip);

        // Assert
        assertEquals(expectedCountryDto, actualCountryDto);
    }

    @Test
    public void testGetDistances() {
        // Arrange
        List<DistanceResponse> expectedDistances = Arrays.asList(
                new DistanceResponse("test","Colombia", 1000L,1),
                new DistanceResponse("test","Argentina", 2000L,1)
        );
        when(distanceService.getMetrics()).thenReturn(expectedDistances);

        // Act
        List<DistanceResponse> actualDistances = geoController.getDistances();

        // Assert
        assertEquals(expectedDistances, actualDistances);
    }
}
