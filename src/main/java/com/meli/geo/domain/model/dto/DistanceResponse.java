package com.meli.geo.domain.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DistanceResponse {
    private String titulo;
    private String pais;
    private Long distancia;
    private Integer invocaciones;
}
