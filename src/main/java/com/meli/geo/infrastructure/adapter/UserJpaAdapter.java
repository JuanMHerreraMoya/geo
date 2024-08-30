package com.meli.geo.infrastructure.adapter;


import com.meli.geo.application.mapper.UserDtoMapper;
import com.meli.geo.domain.model.constant.CountryConstant;
import com.meli.geo.domain.model.dto.UserDto;
import com.meli.geo.domain.port.UserPersistentPort;
import com.meli.geo.infrastructure.adapter.entity.UserEntity;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import com.meli.geo.infrastructure.adapter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserJpaAdapter implements UserPersistentPort {


    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserJpaAdapter(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }


    @Override
    public UserDto getByIpUser(String ipUser) {
        UserEntity user = userRepository.findByIpUser(ipUser);
        return userDtoMapper.toDto(user);
    }

    @Override
    public void save(UserDto userDto) {
        try {
            userRepository.save(userDtoMapper.toEntity(userDto));
        }catch (Exception e){
            throw new GeoException(HttpStatus.INTERNAL_SERVER_ERROR,String.format(CountryConstant.CURRENT_USER_NOT_SAVED, userDto.getIpUser()));
        }
    }
}
