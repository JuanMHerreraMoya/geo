package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.IpApiDto;

public interface IpApiPersistencePort {
    IpApiDto getDataByIp(String ip);
}
