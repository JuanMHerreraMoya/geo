package com.meli.geo.jpaAdapters;

import com.meli.geo.application.mapper.UserDtoMapper;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.UserDto;
import com.meli.geo.infrastructure.adapter.UserJpaAdapter;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import com.meli.geo.infrastructure.adapter.entity.UserEntity;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import com.meli.geo.infrastructure.adapter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;

public class UserJpaAdapterTest {

    @InjectMocks
    private UserJpaAdapter userJpaAdapter;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByIpUser() {
        String ipUser = "192.168.0.1";
        UserEntity userEntity = new UserEntity(1L, ipUser, 1000, new CountryEntity());
        UserDto userDto = new UserDto(ipUser, 1000, new CountryDto());

        Mockito.when(userRepository.findByIpUser(ipUser)).thenReturn(userEntity);
        Mockito.when(userDtoMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto result = userJpaAdapter.getByIpUser(ipUser);

        assertNotNull(result);
        assertEquals(ipUser, result.getIpUser());
        assertEquals(1000, result.getDistance());
    }

    @Test
    public void testSaveThrowsGeoException() {
        UserDto userDto = new UserDto("192.168.0.1", 1000, new CountryDto());

        Mockito.when(userDtoMapper.toEntity(userDto)).thenThrow(new RuntimeException("Database error"));

        GeoException exception = assertThrows(GeoException.class, () -> userJpaAdapter.save(userDto));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        assertEquals("No se pudo guardar el usuario con ip 192.168.0.1", exception.getMessage());
    }
}
