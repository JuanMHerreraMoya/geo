package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.UserDto;
import com.meli.geo.infrastructure.adapter.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDtoMapper {

    //UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    @Mapping(source = "countryEntity.name", target = "countryName")
    UserDto toDto(UserEntity userEntity);

    @Mapping(source = "countryName", target = "countryEntity.name")
    UserEntity toEntity(UserDto userDto);

    default CountryDto map(String countryName) {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(countryName);
        return countryDto;
    }

    default String map(CountryDto countryDto) {
        return countryDto.getName();
    }
}
