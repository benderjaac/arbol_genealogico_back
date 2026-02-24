package com.arbol.dto;

import com.arbol.models.Union;
import com.arbol.models.UnionChild;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UnionChildDto {
    private Long id;
    private String nombrePersona1;
    private String nombrePersona2;
    private LocalDate fechaInicio;
    private String tipo;
    private String nombreHijo;

    public UnionChildDto(UnionChild unionChild){
        this.id = unionChild.getId();
        Union union = unionChild.getUnion();
        this.nombrePersona1 = union.getPerson1().getNombreCompleto();
        this.nombrePersona2 = union.getPerson2().getNombreCompleto();
        this.fechaInicio = union.getFechaInicio();
        this.tipo = union.getTipo();
        this.nombreHijo = unionChild.getChild().getNombreCompleto();
    }
}
