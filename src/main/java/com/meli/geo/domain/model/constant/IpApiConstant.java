package com.meli.geo.domain.model.constant;

public class IpApiConstant {
    public static final String URL = "http://ip-api.com/json/";
    public static final String FIELDS = "?fields=message,country,countryCode,lat,lon,currency";
    public static final String IpApi_ERROR = "No se logro traer informacion con esta ip %s";
    public static final double buenosAiresLongitud = -58.37723 ;
    public static final double buenosAiresLatitud = -34.61315;
    public static final Integer EARTH_RADIUS_KM = 6371;
}
