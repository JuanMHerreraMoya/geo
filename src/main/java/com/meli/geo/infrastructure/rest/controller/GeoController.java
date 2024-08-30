package com.meli.geo.infrastructure.rest.controller;

import com.meli.geo.application.service.GeoService;
import com.meli.geo.application.usecases.GeoUseCase;
import com.meli.geo.domain.model.dto.CountryDto;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geo")
public class GeoController {
    private final GeoService geoService;

    public GeoController(GeoService geoService) {
        this.geoService = geoService;
    }

    @GetMapping()
    public CountryDto getDataByIp(@PathParam("ip") String ip){
        return geoService.getByIp(ip);
    }

}
