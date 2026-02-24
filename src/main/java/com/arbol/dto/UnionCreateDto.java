package com.arbol.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UnionCreateDto {
    private Long person1Id;
    private Long person2Id; // puede ser null para placeholder
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipo;
}
