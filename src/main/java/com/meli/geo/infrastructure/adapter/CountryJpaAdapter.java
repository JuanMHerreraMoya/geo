package com.meli.geo.infrastructure.adapter;

import com.meli.geo.domain.model.constant.CountryConstant;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.port.CountryPersistencePort;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import com.meli.geo.application.mapper.CountryDtoMapper;
import com.meli.geo.infrastructure.adapter.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
public class CountryJpaAdapter implements CountryPersistencePort {

    private final CountryRepository countryRepository;
    private final CountryDtoMapper countryDtoMapper;

    public CountryJpaAdapter(CountryRepository countryRepository, CountryDtoMapper countryDtoMapper) {
        this.countryRepository = countryRepository;
        this.countryDtoMapper = countryDtoMapper;
    }


    @Override
    public CountryDto getByCountry(String country) {
        CountryEntity countryEntity = countryRepository.findByName(country);
        return countryDtoMapper.toDto(countryEntity);
    }

    @Override
    public void save(CountryDto countryToSave) {
        try {
            countryRepository.save(countryDtoMapper.toEntity(countryToSave));
        }catch (Exception e){
            throw new GeoException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CountryConstant.COUNTRY_NOT_SAVED, countryToSave.getName()));
        }
    }
}
