package com.meli.geo.infrastructure.rest.controller;

import com.meli.geo.application.service.DistanceService;
import com.meli.geo.application.service.GeoService;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.DistanceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/meli/api")
public class FrontController {

    private final GeoService geoService; // Asumiendo que tienes un servicio para manejar la lógica de negocio
    private final DistanceService distanceService;

    public FrontController(GeoService geoService, DistanceService distanceService) {
        this.geoService = geoService;
        this.distanceService = distanceService;
    }

    @GetMapping("/")
    public String home() {
        return "index"; // Muestra la página principal con el formulario
    }

    @GetMapping("/geo")
    public String search(@RequestParam("ip") String ip, Model model) {
        CountryDto countryDto = geoService.getByIp(ip);
        List<DistanceResponse> distanceResponses = distanceService.getMetrics();

        model.addAttribute("distances", distanceResponses);
        model.addAttribute("country", countryDto);

        return "result";
    }
}
