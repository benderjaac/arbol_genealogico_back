package com.arbol.dto;

import com.arbol.models.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonTreeDto {
    private Long id;
    private String nombreCompleto;

    private PersonTreeDto padre;
    private PersonTreeDto madre;

    public PersonTreeDto(Person p) {
        this.id = p.getId();
        this.nombreCompleto = p.getNombre() + " " +
                p.getApellidoPaterno() + " " +
                p.getApellidoMaterno();
    }
}
