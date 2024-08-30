package com.meli.geo.infrastructure.adapter.externalservices;

import com.meli.geo.application.mapper.IpApiDtoMapper;
import com.meli.geo.domain.model.constant.IpApiConstant;
import com.meli.geo.domain.model.dto.IpApiDto;
import com.meli.geo.domain.port.IpApiPersistencePort;
import com.meli.geo.infrastructure.adapter.entity.IpApiEntity;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IpApi implements IpApiPersistencePort {
    private final IpApiDtoMapper ipApiDtoMapper;

    public IpApi(IpApiDtoMapper ipApiDtoMapper, IpApiConstant ipApiConstant) {
        this.ipApiDtoMapper = ipApiDtoMapper;
    }

    @Override
    public IpApiDto getDataByIp(String ip) {

        RestTemplate restTemplate = new RestTemplate();
        String url = IpApiConstant.URL.concat(ip).concat(IpApiConstant.FIELDS);
        ResponseEntity<IpApiEntity> response = restTemplate.getForEntity(url, IpApiEntity.class);
        if(!(response.getStatusCode().value() ==200)){
            throw new GeoException(HttpStatus.BAD_REQUEST, String.format(IpApiConstant.IpApi_ERROR, ip));
        }
        return ipApiDtoMapper.toDto(response.getBody());
    }
}
