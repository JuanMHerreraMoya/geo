package com.meli.geo.application.service;

import com.meli.geo.application.usecases.GeoUseCase;
import com.meli.geo.domain.model.constant.IpApiConstant;
import com.meli.geo.domain.model.dto.*;
import com.meli.geo.domain.port.*;
import com.meli.geo.infrastructure.adapter.exception.GeoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeoService implements GeoUseCase {

    private final CountryPersistencePort countryPersistencePort;
    private final IpApiPersistencePort ipApiPersistencePort;
    private final CountryLanguagesPort countryLanguagesPort;
    private final MathDistance mathDistance;
    private final UserPersistentPort userPersistentPort;
    private final LanguagesPersistentPort languagesPersistentPort;
    private final ExchangeFixerPort exchangeFixerPort;
    private final CurrencyExchangePersistentPort currencyExchangePersistentPort;
    private final TimeZonePersistentPort timeZonePersistentPort;

    @Autowired
    public GeoService(CountryPersistencePort countryPersistencePort, IpApiPersistencePort ipApiPersistencePort, CountryLanguagesPort countryLanguagesPort, MathDistance mathDistance, UserPersistentPort userPersistentPort, LanguagesPersistentPort languagesPersistentPort, ExchangeFixerPort exchangeFixerPort, CurrencyExchangePersistentPort currencyExchangePersistentPort, TimeZonePersistentPort timeZonePersistentPort) {
        this.countryPersistencePort = countryPersistencePort;
        this.ipApiPersistencePort = ipApiPersistencePort;
        this.countryLanguagesPort = countryLanguagesPort;
        this.mathDistance = mathDistance;
        this.userPersistentPort = userPersistentPort;
        this.languagesPersistentPort = languagesPersistentPort;
        this.exchangeFixerPort = exchangeFixerPort;
        this.currencyExchangePersistentPort = currencyExchangePersistentPort;
        this.timeZonePersistentPort = timeZonePersistentPort;
    }

    @Override
    public CountryDto getByIp(String ip) {

        UserDto userDtoDB = userPersistentPort.getByIpUser(ip);
        List<CurrencyExchangeDto> currencyExchangeDtoList = validateExchange();
        if(Objects.nonNull(userDtoDB)){
            return validateAnswer(userDtoDB.getCountry(), currencyExchangeDtoList);
        }
        else{
            IpApiDto responseIpApi = ipApiPersistencePort.getDataByIp(ip);
            if(Objects.isNull(responseIpApi) || Objects.isNull(responseIpApi.getCountry())){
                throw new GeoException(HttpStatus.BAD_REQUEST, String.format(IpApiConstant.IpApi_ERROR,ip));
            }
            CountryDto response = validateAnswer(responseIpApi, currencyExchangeDtoList);
            userPersistentPort.save(new UserDto(ip,response.getDistance(),response));
            return response;
        }
    }

    private List<CurrencyExchangeDto> validateExchange() {
        List<CurrencyExchangeDto> exchangeDtos = currencyExchangePersistentPort.getAll();
        Calendar calendar = Calendar.getInstance();
        Date hoy = calendar.getTime();
        if(exchangeDtos.isEmpty() || exchangeDtos.getFirst().getLastUpdate().before(hoy)){
            ExchangeResponse exchangeResponse = exchangeFixerPort.getAllExchange();
            return createCurrencyExchange(exchangeResponse.getConversion_rates());
        }
        return exchangeDtos;
    }

    private List<CurrencyExchangeDto> createCurrencyExchange(Map<String,Object> rates) {
        List<CurrencyExchangeDto> currencyExchangeDtoList = new ArrayList<>();
        rates.forEach((s, o) -> {
            CurrencyExchangeDto currencyExchangeDto = new CurrencyExchangeDto(s,o.toString(),new Date());
            currencyExchangePersistentPort.save(currencyExchangeDto);
            currencyExchangeDtoList.add(currencyExchangeDto);
        });
        return currencyExchangeDtoList;
    }

    private CountryDto validateAnswer(CountryDto countryDb, List<CurrencyExchangeDto> currencyExchangeDtoList) {
        countryDb.setInvocation(countryDb.getInvocation()+1);
        CountryDto countryDbSaved = countryPersistencePort.save(countryDb);
        CurrencyExchangeDto currencyExchangeUsd = currencyExchangeDtoList.stream().filter(c -> c.getIso().equals(countryDbSaved.getCurrency())).toList().getFirst();
        countryDbSaved.setUsdExchange(currencyExchangeUsd.getUsdPrice());
        RestCountriesDto restCountriesDto = countryLanguagesPort.getLanguagesByCountry(countryDbSaved.getIso());
        countryDbSaved.setLanguageNames(convertLanguages(restCountriesDto.getLenguages(),countryDbSaved));
        countryDbSaved.setTimezone(convertTimeZones(restCountriesDto.getTimeZones(),countryDbSaved));
        return countryDbSaved;
    }


    private CountryDto validateAnswer(IpApiDto responseIpApi, List<CurrencyExchangeDto> currencyExchangeDtoList) {
        CountryDto countryDb = findCountry(responseIpApi.getCountry());
        CurrencyExchangeDto currencyExchangeUsd = currencyExchangeDtoList.stream().filter(c -> c.getIso().equals(responseIpApi.getCurrency())).toList().getFirst();
        if (Objects.isNull(countryDb)){
            CountryDto countryToSave = new CountryDto(responseIpApi.getCountry(),responseIpApi.getCountryCode()
                    ,validateDistance(responseIpApi.getLon(),responseIpApi.getLat())
                    ,1,responseIpApi.getCurrency(),null,null);

            RestCountriesDto languagesTimezone = countryLanguagesPort.getLanguagesByCountry(responseIpApi.getCountryCode());
            countryToSave.setTimezone(convertTimeZones(languagesTimezone.getTimeZones(),countryToSave));
            countryToSave.setLanguageNames(convertLanguages(languagesTimezone.getLenguages(),countryToSave));
            countryToSave = countryPersistencePort.save(countryToSave);
            countryToSave.setUsdExchange(currencyExchangeUsd.getUsdPrice());
            return countryToSave;
        }else {
            countryDb.setInvocation(countryDb.getInvocation()+1);
            countryPersistencePort.save(countryDb);
            List<Object> listTimeZone = countryDb.getTimezone().stream().map(TimezoneDto::getUtc).collect(Collectors.toUnmodifiableList());
            countryDb.setTimezone(convertTimeZones(listTimeZone,countryDb));
            countryDb.setUsdExchange(currencyExchangeUsd.getUsdPrice());
            return countryDb;
        }
    }

    private Set<TimezoneDto> convertTimeZones(List<Object> timeZones, CountryDto countryToSave) {
        List<TimezoneDto> timezoneDtoList = timeZonePersistentPort.getAll();
        List<String> timeNameList = timezoneDtoList.stream().map(TimezoneDto::getUtc).toList();
        Set<TimezoneDto> response = new HashSet<>();
        timeZones.forEach(o -> {
            if(Objects.nonNull(o) && !timeNameList.contains(o.toString())){
                TimezoneDto timezoneDtoToSave = new TimezoneDto(o.toString(),Set.of(countryToSave.getName()),convertZoneDto(o.toString()),new Date());
                response.add(timeZonePersistentPort.save(timezoneDtoToSave));
            } else if (Objects.nonNull(o) && timeNameList.contains(o.toString())) {
                TimezoneDto timezoneDtoDb = timezoneDtoList.stream().filter(timezoneDto -> timezoneDto.getUtc().equals(o.toString())).toList().getFirst();
                List<String> countriesExistentOnTimeZones = getCountryName(timezoneDtoDb.getCountryNames(),countryToSave.getName());
                timezoneDtoDb.setCountryNames(Set.of(countryToSave.getName()));
                timezoneDtoDb.setLastUpdate(new Date());
                response.add(timeZonePersistentPort.save(timezoneDtoDb));
                }
            }
        );
        return response;
    }

    private Set<LanguageDto> convertLanguages(Map<String, Object> lenguages, CountryDto countryToSave) {

        List<LanguageDto> languageDtoList = languagesPersistentPort.getAll();
        List<String> languagesNames = languageDtoList.stream().map(LanguageDto::getName).toList();
        Set<LanguageDto> response = new HashSet<>();
        lenguages.forEach((s, o) -> {
            if(!languagesNames.contains(o.toString())){
                LanguageDto languageDtoToSave = new LanguageDto(o.toString(),s,Set.of(countryToSave.getName()));
                response.add(languagesPersistentPort.save(languageDtoToSave));
            } else if (languagesNames.contains(o.toString())) {
                LanguageDto languageDtoDb = languagesPersistentPort.getByName(o.toString());
                List<String> countriesExistentOnLanguages = getCountryName(languageDtoDb.getCountryNames(),countryToSave.getName());
                if(!countriesExistentOnLanguages.contains(countryToSave.getName())){
                    countriesExistentOnLanguages.add(countryToSave.getName());
                    languageDtoDb.setCountryNames(Set.of(String.valueOf(countriesExistentOnLanguages)));
                    response.add(languagesPersistentPort.save(languageDtoDb));
                }else{
                    response.add(languageDtoDb);
                }

            }
        });
        return response;
    }

    private List<String> convertZone(Set<TimezoneDto> timeZones) {
        List<String> horas = new ArrayList<>();
        timeZones.forEach(o -> {
            ZoneId zoneId = ZoneId.of(o.getUtc());
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedDateTime = zonedDateTime.format(formatter);
            horas.add(formattedDateTime);
        });
        return horas;
    }

    private Date convertZoneDto(String utc){
        ZoneId zoneId = ZoneId.of(utc);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }


    private List<String> getCountryName(Set<String> countryNames, String country) {
        if (Objects.isNull(countryNames)){
            return List.of(country);
        }
        return new ArrayList<>(countryNames);
    }

    private Integer validateDistance(double lon, double lat) {
        return mathDistance.getDistance(lon,lat);
    }

    private CountryDto findCountry(String country) {
        return countryPersistencePort.getByCountry(country);
    }
}
