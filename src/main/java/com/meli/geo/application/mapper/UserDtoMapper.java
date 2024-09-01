package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.UserDto;
import com.meli.geo.infrastructure.adapter.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDtoMapper {

    @Mapping(source = "country", target = "country")
    UserDto toDto(UserEntity userEntity);

    // Mapear UserDto a UserEntity
    @Mapping(source = "country", target = "country")
    UserEntity toEntity(UserDto userDto);


}
