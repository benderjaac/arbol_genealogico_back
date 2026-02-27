package com.arbol.dto;

import com.arbol.models.Person;
import com.arbol.models.User;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonSimpleDto {
    private Long id;
    private String nombreCompleto;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String alias;

    private Long padre_id=0L;
    private Long madre_id=0L;
    private String padreNombreCompleto="";
    private String madreNombreCompleto="";

    private LocalDate fechaNacimiento;
    private String genero;
    private String foto;
    private String lugarNacimiento;
    private String notas;


    public PersonSimpleDto(Person person) {
        this.id = person.getId();
        this.nombre = person.getNombre();
        this.apellidoPaterno = person.getApellidoPaterno();
        this.apellidoMaterno = person.getApellidoMaterno();
        this.nombreCompleto = person.getNombre() + " " +
                (person.getApellidoPaterno() != null ? person.getApellidoPaterno() : "") + " " +
                (person.getApellidoMaterno() != null ? person.getApellidoMaterno() : "");

        this.alias = person.getAlias();

        Person padre =  person.getPadre();
        if(padre!=null){
            this.padre_id=padre.getId();
            this.padreNombreCompleto = padre.getNombre()+' '+padre.getApellidoPaterno()+' '+padre.getApellidoPaterno();
        }

        Person madre =  person.getMadre();
        if(madre!=null){
            this.madre_id=madre.getId();
            this.madreNombreCompleto = madre.getNombre()+' '+madre.getApellidoPaterno()+' '+madre.getApellidoPaterno();
        }

        this.fechaNacimiento=person.getFechaNacimiento();
        this.genero= person.getGenero();
        this.foto= person.getFoto();
        this.lugarNacimiento= person.getLugarNacimiento();
        this.notas=person.getNotas();
    }
}
