package com.meli.geo.infrastructure.adapter.externalservices;

import com.meli.geo.domain.model.constant.CountryConstant;
import com.meli.geo.domain.model.dto.ExchangeResponse;
import com.meli.geo.domain.port.ExchangeFixerPort;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;


@Component
public class ExchangeFixer implements ExchangeFixerPort {


    @Autowired
    public ExchangeResponse getAllExchange() {

        RestTemplate restTemplate = new RestTemplate();
        String url = CountryConstant.URL_EXCHANGE;
        ResponseEntity<ExchangeResponse> response = restTemplate.getForEntity(url, ExchangeResponse.class);
        if(!(response.getStatusCode().value() ==200)){
            throw new GeoException(HttpStatus.BAD_REQUEST, CountryConstant.ERROR_EXCHANGE);
        }
        return new ExchangeResponse(Objects.requireNonNull(response.getBody()).getConversion_rates(),new Date());
    }
}
