package com.meli.geo.config;

import com.meli.geo.application.mapper.*;
import com.meli.geo.domain.model.constant.CountryConstant;
import com.meli.geo.domain.model.constant.CountryLanguagesConstant;
import com.meli.geo.domain.model.constant.IpApiConstant;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public CountryDtoMapper countryDtoMapper() {
        return Mappers.getMapper(CountryDtoMapper.class);
    }
    @Bean
    public CurrencyExchangeDtopMapper currencyExchangeDtopMapper() {
        return Mappers.getMapper(CurrencyExchangeDtopMapper.class);
    }
    @Bean
    public UserDtoMapper userDtoMapper() {
        return Mappers.getMapper(UserDtoMapper.class);
    }
    @Bean
    public IpApiDtoMapper ipApiDtoMapper() {
        return Mappers.getMapper(IpApiDtoMapper.class);
    }

    @Bean
    public LanguageDtoMapper languageDtoMapper() {
        return Mappers.getMapper(LanguageDtoMapper.class);
    }

    @Bean
    public RestCountryDtoMapper restCountryDtoMapper() {
        return Mappers.getMapper(RestCountryDtoMapper.class);
    }
    @Bean
    public IpApiConstant ipApiConstant() {
        return new IpApiConstant();
    }
    @Bean
    public CountryLanguagesConstant countryLanguagesConstant() {
        return new CountryLanguagesConstant();
    }
    @Bean
    public CountryConstant countryConstant() {
        return new CountryConstant();
    }
}
