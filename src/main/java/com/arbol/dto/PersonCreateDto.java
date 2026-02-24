package com.arbol.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PersonCreateDto {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String alias;
    private LocalDate fechaNacimiento;
    private String genero;
    private String foto;
    private String lugarNacimiento;
    private String notas;

}
