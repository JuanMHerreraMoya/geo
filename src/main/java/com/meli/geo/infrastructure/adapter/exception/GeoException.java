package com.meli.geo.infrastructure.adapter.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Setter
@Getter
public class GeoException  extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus errorCode;
    private String errorMessage;

    public GeoException(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
