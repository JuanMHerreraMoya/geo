package com.meli.geo.rest;

import com.meli.geo.application.service.DistanceService;
import com.meli.geo.application.service.GeoService;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.DistanceResponse;
import com.meli.geo.infrastructure.rest.controller.FrontController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FrontControllerTest {

    @Mock
    private GeoService geoService;

    @Mock
    private DistanceService distanceService;

    @Mock
    private Model model;

    @InjectMocks
    private FrontController frontController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(frontController).build();
    }

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/meli/api/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testSearch() throws Exception {
        String ip = "192.168.0.1";
        CountryDto countryDto = new CountryDto();
        countryDto.setName("Colombia");

        List<DistanceResponse> distanceResponses = Arrays.asList(
                new DistanceResponse("test","Argentina", (long) 500.0,1),
                new DistanceResponse("test" ,"Brazil", (long) 1000.0,1)
        );

        when(geoService.getByIp(ip)).thenReturn(countryDto);
        when(distanceService.getMetrics()).thenReturn(distanceResponses);

        mockMvc.perform(get("/meli/api/geo").param("ip", ip))
                .andExpect(status().isOk())
                .andExpect(model().attribute("country", countryDto))
                .andExpect(model().attribute("distances", distanceResponses))
                .andExpect(view().name("result"));

        verify(geoService, times(1)).getByIp(ip);
        verify(distanceService, times(1)).getMetrics();
    }
}
