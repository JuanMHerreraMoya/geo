package com.meli.geo.infrastructure.adapter.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class GeoException  extends RuntimeException {

    private HttpStatus status;

    public GeoException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
