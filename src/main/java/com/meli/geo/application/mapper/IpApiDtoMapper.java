package com.meli.geo.application.mapper;

import com.meli.geo.domain.model.dto.IpApiDto;
import com.meli.geo.infrastructure.adapter.entity.IpApiEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IpApiDtoMapper {

    IpApiDto toDto(IpApiEntity ipApiEntity);
    IpApiEntity toEntity(IpApiDto ipApiDto);
}
