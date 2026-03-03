package com.arbol.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id")
    private Person padre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "madre_id")
    private Person madre;

    @Column(nullable = false)
    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String genero;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_photo_id")
    private Photo mainPhoto;

    private String lugarNacimiento;

    private String notas;

    @Column(nullable = false)
    private Boolean placeholder = false;

    @Formula("concat(nombre, ' ', apellido_paterno, ' ', apellido_materno)")
    private String nombreCompleto;

    public String getNombreCompleto() {
        return nombreCompleto;
    }
// https://code.balkan.app/family-tree-js/royal-family-tree#JS
}

