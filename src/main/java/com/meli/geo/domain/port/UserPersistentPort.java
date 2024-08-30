package com.meli.geo.domain.port;

import com.meli.geo.domain.model.dto.UserDto;

public interface UserPersistentPort {
    UserDto getByIpUser(String ipUser);
    void save(UserDto userDto);
}
