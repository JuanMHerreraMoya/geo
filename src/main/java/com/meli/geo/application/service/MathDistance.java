package com.meli.geo.application.service;

import com.meli.geo.domain.model.constant.IpApiConstant;
import org.springframework.stereotype.Service;

@Service
public class MathDistance {
    public Integer getDistance(double longCountry, double latCountry){

        double dLat = Math.toRadians(latCountry - IpApiConstant.buenosAiresLatitud);
        double dLon = Math.toRadians(longCountry - IpApiConstant.buenosAiresLongitud);

        //diferencia angular entre los dos puntos
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(IpApiConstant.buenosAiresLatitud)) * Math.cos(Math.toRadians(latCountry))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        //ángulo central en radianes
        //atan--> Arco tangente de dos parametros
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanceInKm = IpApiConstant.EARTH_RADIUS_KM * c;
        return (int) Math.round(distanceInKm);
    }
}
