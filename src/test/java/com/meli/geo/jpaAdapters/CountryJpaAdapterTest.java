package com.meli.geo.jpaAdapters;

import com.meli.geo.application.mapper.CountryDtoMapper;
import com.meli.geo.domain.model.dto.CountryDto;
import com.meli.geo.domain.model.dto.LanguageDto;
import com.meli.geo.domain.model.dto.TimezoneDto;
import com.meli.geo.infrastructure.adapter.CountryJpaAdapter;
import com.meli.geo.infrastructure.adapter.entity.CountryEntity;
import com.meli.geo.infrastructure.adapter.entity.LanguagesEntity;
import com.meli.geo.infrastructure.adapter.entity.TimeZoneEntity;
import com.meli.geo.infrastructure.adapter.entity.UserEntity;
import com.meli.geo.infrastructure.adapter.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;


public class CountryJpaAdapterTest {

    @InjectMocks
    private CountryJpaAdapter countryJpaAdapter;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryDtoMapper countryDtoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetByCountry() {
        String countryName = "Argentina";
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(countryName);
        CountryDto countryDto = new CountryDto();
        countryDto.setName(countryName);

        Mockito.when(countryRepository.findByName(countryName)).thenReturn(countryEntity);
        Mockito.when(countryDtoMapper.toDto(countryEntity)).thenReturn(countryDto);

        CountryDto result = countryJpaAdapter.getByCountry(countryName);

        assertNotNull(result);
        assertEquals(countryName, result.getName());
    }

    @Test
    public void testSave() {
        CountryDto countryDto = new CountryDto();
        countryDto.setName("Argentina");
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName("Argentina");

        Mockito.when(countryDtoMapper.toEntity(countryDto)).thenReturn(countryEntity);
        Mockito.when(countryRepository.save(countryEntity)).thenReturn(countryEntity);
        Mockito.when(countryDtoMapper.toDto(countryEntity)).thenReturn(countryDto);

        CountryDto result = countryJpaAdapter.save(countryDto);

        assertNotNull(result);
        assertEquals(countryDto.getName(), result.getName());
    }


    private CountryEntity getCountry(){
        return new CountryEntity(1L,"Colombia","CO",1000, 1,"COP",
                Set.of(new LanguagesEntity(1L,"Esp","Esp",null)),
                Set.of(new TimeZoneEntity(1L,"COP","4080",new Date(),null)),
                List.of(new UserEntity(1L,"1.1.1.1",10,null)));
    }
    private CountryDto getCountryDto(){
        return new CountryDto(1L,"Colombia",1000, "CO",1,"COP","4080",
                Set.of(new LanguageDto(1L,"Esp","Esp",null)),
                Set.of(new TimezoneDto(1L,"COP","4080",new Date(),null)));
    }

    @Test
    public void testGetAll() {
        List<CountryEntity> countryEntities = List.of(getCountry());
        List<CountryDto> countryDtos = List.of(getCountryDto());

        Mockito.when(countryRepository.findAll()).thenReturn(countryEntities);
        Mockito.when(countryDtoMapper.toDto(any(CountryEntity.class))).thenAnswer(invocation -> {
            CountryEntity entity = invocation.getArgument(0);
            return getCountryDto();
        });

        List<CountryDto> result = countryJpaAdapter.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Colombia", result.get(0).getName());
    }

    @Test
    public void testGetMax() {
        Object[] maxCountry = {"Brasil", 10, 5000L};

        Mockito.when(countryRepository.getMax()).thenReturn(maxCountry);

        Object[] result = countryJpaAdapter.getMax();

        assertNotNull(result);
        assertEquals("Brasil", result[0]);
        assertEquals(10, result[1]);
        assertEquals(5000L, result[2]);
    }

    @Test
    public void testGetMin() {
        Object[] minCountry = {"Chile", 1, 300L};

        Mockito.when(countryRepository.getMin()).thenReturn(minCountry);

        Object[] result = countryJpaAdapter.getMin();

        assertNotNull(result);
        assertEquals("Chile", result[0]);
        assertEquals(1, result[1]);
        assertEquals(300L, result[2]);
    }
}
