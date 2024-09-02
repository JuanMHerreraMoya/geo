package com.meli.geo.infrastructure.rest.controller;

import com.meli.geo.application.service.DistanceService;
import com.meli.geo.application.service.GeoService;
import com.meli.geo.application.usecases.GeoUseCase;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.DistanceResponse;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meli/api/geologia")
public class GeoController {
    private final GeoService geoService;
    private final DistanceService distanceService;

    public GeoController(GeoService geoService, DistanceService distanceService) {
        this.geoService = geoService;
        this.distanceService = distanceService;
    }

    @GetMapping()
    public CountryDto getDataByIp(@PathParam("ip") String ip) throws GeoException {
        return geoService.getByIp(ip);
    }
    @GetMapping("/distance")
    public List<DistanceResponse> getDistances(){
        return distanceService.getMetrics();
    }


}
