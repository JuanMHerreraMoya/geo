package com.meli.geo.infrastructure.adapter.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private HttpStatus status;

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
