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

    @Autowired
    public GeoService(CountryPersistencePort countryPersistencePort, IpApiPersistencePort ipApiPersistencePort, CountryLanguagesPort countryLanguagesPort, MathDistance mathDistance, UserPersistentPort userPersistentPort, LanguagesPersistentPort languagesPersistentPort, ExchangeFixerPort exchangeFixerPort, CurrencyExchangePersistentPort currencyExchangePersistentPort) {
        this.countryPersistencePort = countryPersistencePort;
        this.ipApiPersistencePort = ipApiPersistencePort;
        this.countryLanguagesPort = countryLanguagesPort;
        this.mathDistance = mathDistance;
        this.userPersistentPort = userPersistentPort;
        this.languagesPersistentPort = languagesPersistentPort;
        this.exchangeFixerPort = exchangeFixerPort;
        this.currencyExchangePersistentPort = currencyExchangePersistentPort;
    }

    @Override
    public CountryDto getByIp(String ip) {
        //Validar que la ip este en la DB o no
        UserDto userDtoDB = userPersistentPort.getByIpUser(ip);
        List<CurrencyExchangeDto> currencyExchangeDtoList = validateExchange();
        if(Objects.nonNull(userDtoDB)){
            return validateAnswer(userDtoDB.getCountryName(), currencyExchangeDtoList);
        }
        else{
            IpApiDto responseIpApi = ipApiPersistencePort.getDataByIp(ip);
            if(Objects.isNull(responseIpApi)){
                throw new GeoException(HttpStatus.BAD_REQUEST,String.format(IpApiConstant.IpApi_ERROR,ip));
            }
            return validateAnswer(responseIpApi, currencyExchangeDtoList);
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
        countryPersistencePort.save(countryDb);
        CurrencyExchangeDto currencyExchangeUsd = currencyExchangeDtoList.stream().filter(c -> c.getIso().equals(countryDb.getCurrency())).toList().getFirst();
        countryDb.setUsdExchange(currencyExchangeUsd.getUsdPrice());
        //countryDb.setTimeList(convertZone(countryDb.getTimeList().getTimeZones()));
        return countryDb;
    }


    private CountryDto validateAnswer(IpApiDto responseIpApi, List<CurrencyExchangeDto> currencyExchangeDtoList) {
        CountryDto countryDb = findCountry(responseIpApi.getCountry());
        CurrencyExchangeDto currencyExchangeUsd = currencyExchangeDtoList.stream().filter(c -> c.getIso().equals(responseIpApi.getCurrency())).toList().getFirst();
        if (Objects.isNull(countryDb)){
            CountryDto countryToSave = new CountryDto(responseIpApi.getCountry(),responseIpApi.getCountryCode()
                    ,validateDistance(responseIpApi.getLon(),responseIpApi.getLat())
                    ,1,responseIpApi.getCurrency(),null);

            RestCountriesDto languagesTimezone = countryLanguagesPort.getLanguagesByCountry(responseIpApi.getCountryCode());
            countryToSave.setLanguageNames(convertLanguages(languagesTimezone.getLenguages(),countryToSave));
            countryToSave.setUsdExchange(currencyExchangeUsd.getUsdPrice());
            countryToSave.setTimeList(convertZone(languagesTimezone.getTimeZones()));
            countryPersistencePort.save(countryToSave);
            return countryToSave;
        }else {
            countryDb.setInvocation(countryDb.getInvocation()+1);
            countryPersistencePort.save(countryDb);
            countryDb.setUsdExchange(currencyExchangeUsd.getUsdPrice());
            return countryDb;
        }
    }

    private List<String> convertZone(List<Object> timeZones) {
        List<String> horas = new ArrayList<>();
        timeZones.forEach(o -> {
            ZoneId zoneId = ZoneId.of(o.toString());
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedDateTime = zonedDateTime.format(formatter);
            horas.add(formattedDateTime);
        });
        return horas;
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
