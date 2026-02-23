package com.arbol.dto;

import com.arbol.models.Person;
import com.arbol.models.Union;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UnionDto {
    private Long id;
    private String nombrePersona1;
    private String nombrePersona2;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String tipo;

    public UnionDto(Union union){
        this.id = union.getId();
        this.nombrePersona1 = union.getPerson1().getNombreCompleto();
        this.nombrePersona2 = union.getPerson2().getNombreCompleto();
        this.fechaInicio = union.getFechaInicio();
        this.fechaFin = union.getFechaFin();
        this.tipo = union.getTipo();
    }
}
