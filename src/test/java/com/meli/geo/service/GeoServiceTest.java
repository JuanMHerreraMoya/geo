package com.meli.geo.service;

import com.meli.geo.application.service.GeoService;
import com.meli.geo.application.service.MathDistance;
import com.meli.geo.domain.model.dto.*;
import com.meli.geo.domain.port.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeoServiceTest {

    @InjectMocks
    private GeoService geoService;

    @Mock
    private CountryPersistencePort countryPersistencePort;

    @Mock
    private IpApiPersistencePort ipApiPersistencePort;

    @Mock
    private CountryLanguagesPort countryLanguagesPort;

    @Mock
    private MathDistance mathDistance;

    @Mock
    private UserPersistentPort userPersistentPort;

    @Mock
    private LanguagesPersistentPort languagesPersistentPort;

    @Mock
    private ExchangeFixerPort exchangeFixerPort;

    @Mock
    private CurrencyExchangePersistentPort currencyExchangePersistentPort;

    @Mock
    private TimeZonePersistentPort timeZonePersistentPort;

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks
        countryPersistencePort = Mockito.mock(CountryPersistencePort.class);
        ipApiPersistencePort = Mockito.mock(IpApiPersistencePort.class);
        countryLanguagesPort = Mockito.mock(CountryLanguagesPort.class);
        mathDistance = Mockito.mock(MathDistance.class);
        userPersistentPort = Mockito.mock(UserPersistentPort.class);
        languagesPersistentPort = Mockito.mock(LanguagesPersistentPort.class);
        exchangeFixerPort = Mockito.mock(ExchangeFixerPort.class);
        currencyExchangePersistentPort = Mockito.mock(CurrencyExchangePersistentPort.class);
        timeZonePersistentPort = Mockito.mock(TimeZonePersistentPort.class);

        geoService = new GeoService(
                countryPersistencePort,
                ipApiPersistencePort,
                countryLanguagesPort,
                mathDistance,
                userPersistentPort,
                languagesPersistentPort,
                exchangeFixerPort,
                currencyExchangePersistentPort,
                timeZonePersistentPort
        );
    }

    private CountryDto getCountry(){
        return new CountryDto(1L,"Colombia",1000, "CO",1,"COP","4080",
                Set.of(new LanguageDto(1L,"Esp","Esp",null)),
                Set.of(new TimezoneDto(1L,"COP","4080",new Date(),null)));
    }

    private RestCountriesDto getRestCountries(){
        RestCountriesDto restCountriesDto = new RestCountriesDto();
        restCountriesDto.setLenguages(getLanguages());
        restCountriesDto.setTimeZones(getTimeZones());
        return restCountriesDto;
    }

    private List<Object> getTimeZones() {
        List<Object> timeZones = new ArrayList<>();
        timeZones.add("UTC-09:00");
        return timeZones;
    }

    private Map<String,Object> getLanguages(){
        Map<String,Object> lenguages = new HashMap<>();
        lenguages.put("English","eng");
        return lenguages;
    }

    @Test
    public void testGetByIp_UserExists() {
        // Arrange
        String ip = "192.168.1.1";
        UserDto userDto = new UserDto(1L,ip, 1000, getCountry());
        List<CurrencyExchangeDto> currencyExchangeDtoList = Collections.emptyList();
        Map<String,Object> conversion_rates = new HashMap<>();
        conversion_rates.put("COP",4080);
        when(userPersistentPort.getByIpUser(ip)).thenReturn(userDto);
        when(currencyExchangePersistentPort.getAll()).thenReturn(currencyExchangeDtoList);
        when(countryPersistencePort.save(any())).thenReturn(getCountry());
        when(countryLanguagesPort.getLanguagesByCountry(any())).thenReturn(getRestCountries());
        when(exchangeFixerPort.getAllExchange()).thenReturn(new ExchangeResponse(conversion_rates,new Date()));

        // Act
        CountryDto result = geoService.getByIp(ip);

        // Assert
        assertNotNull(result);
        assertEquals("Colombia", result.getName());
    }

    @Test
    public void testGetByIp_IpNotFound() {
        // Arrange
        String ip = "192.168.1.1";
        IpApiDto ipApiDto = new IpApiDto("Colombia", "CO",1.0,1.0,1,"COP");
        List<CurrencyExchangeDto> currencyExchangeDtoList = Collections.emptyList();
        CountryDto countryDto = getCountry();
        Map<String,Object> conversion_rates = new HashMap<>();
        conversion_rates.put("COP",4080);
        when(userPersistentPort.getByIpUser(ip)).thenReturn(null);
        when(ipApiPersistencePort.getDataByIp(ip)).thenReturn(ipApiDto);
        when(currencyExchangePersistentPort.getAll()).thenReturn(currencyExchangeDtoList);
        when(exchangeFixerPort.getAllExchange()).thenReturn(new ExchangeResponse(conversion_rates,new Date()));
        when(countryPersistencePort.getByCountry(ipApiDto.getCountry())).thenReturn(null);
        when(countryLanguagesPort.getLanguagesByCountry(ipApiDto.getCountryCode())).thenReturn(new RestCountriesDto(new HashMap<>(), new ArrayList<>()));
        when(mathDistance.getDistance(ipApiDto.getLon(), ipApiDto.getLat())).thenReturn(1000);
        doNothing().when(currencyExchangePersistentPort).save(any(CurrencyExchangeDto.class));
        when(countryPersistencePort.save(any(CountryDto.class))).thenReturn(countryDto);
        doNothing().when(userPersistentPort).save(any(UserDto.class));

        // Act
        CountryDto result = geoService.getByIp(ip);

        // Assert
        assertNotNull(result);
        assertEquals("Colombia", result.getName());
    }

}
