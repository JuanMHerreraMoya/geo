package com.meli.geo.application.service;

import com.meli.geo.domain.model.constant.CountryConstant;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.DistanceResponse;
import com.meli.geo.domain.port.CountryPersistencePort;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DistanceService {
    private final CountryPersistencePort countryPersistencePort;

    public DistanceService(CountryPersistencePort countryPersistencePort) {
        this.countryPersistencePort = countryPersistencePort;
    }

    public List<DistanceResponse> getMetrics() {
        List<DistanceResponse> response = new ArrayList<>();
        List<CountryDto> countryDtoList = countryPersistencePort.getAll();
        if(countryDtoList.size()==0){
            throw new GeoException(HttpStatus.BAD_REQUEST, CountryConstant.ERROR_NO_DATA);
        }
        response.add(calculateShortestDistance(countryDtoList));
        response.add(calculateMaxDistant(countryDtoList));
        response.add(calculateAverage(countryDtoList));
        return response;
    }

    private DistanceResponse calculateAverage(List<CountryDto> countryDtoList) {
        int average = 0;
        int averageInvocation = 0;
        for (CountryDto countryDto : countryDtoList) {
            average += countryDto.getDistance() * countryDto.getInvocation();
            averageInvocation += countryDto.getInvocation();
        }
        Long averageDistance = (long) (average/averageInvocation);
        return new DistanceResponse("Promedio de distancia","todos",averageDistance,averageInvocation);
    }

    private DistanceResponse calculateMaxDistant(List<CountryDto> countryDtoList) {
        Object[] max= countryPersistencePort.getMax();
        Object[] countryDetails = (Object[]) max[0];
        return new DistanceResponse("Distancia Maxima",countryDetails[0].toString(),Long.valueOf(countryDetails[2].toString()),Integer.valueOf(countryDetails[1].toString()));
    }

    private DistanceResponse calculateShortestDistance(List<CountryDto> countryDtoList) {
        Object[] min = countryPersistencePort.getMin();
        Object[] countryDetails = (Object[]) min[0];
        return new DistanceResponse("Distancia Minima",countryDetails[0].toString(),Long.valueOf(countryDetails[2].toString()),Integer.valueOf(countryDetails[1].toString()));
    }
}
