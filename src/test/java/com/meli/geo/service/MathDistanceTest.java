package com.meli.geo.service;

import com.meli.geo.application.service.MathDistance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathDistanceTest {

    private MathDistance mathDistance;

    @BeforeEach
    public void setUp() {
        mathDistance = new MathDistance();
    }

    @Test
    public void testGetDistance() {
        //Arrange Buenos Aires (lat: -34.6037, lon: -58.3816)
        double latCountry = -34.6037;
        double lonCountry = -58.3816;
        double expectedDistance = 1.0;
        // Act
        int actualDistance = mathDistance.getDistance(lonCountry, latCountry);
        // Assert
        assertEquals(expectedDistance, actualDistance, "La distancia calculada debe ser 0 para las mismas coordenadas");
    }

    @Test
    public void testGetDistance_AnotherLocation() {
        //Arrange  Bogotá (lat: 4.6097, lon: -74.0817)
        double latCountry = 4.6097;
        double lonCountry = -74.0817;
        double expectedDistance = 4661.0;
        // Act
        int actualDistance = mathDistance.getDistance(lonCountry, latCountry);
        // Assert
        assertEquals(expectedDistance, actualDistance, 50, "La distancia calculada entre Buenos Aires y Bogotá debería estar cerca de 4387 km");
    }
}
