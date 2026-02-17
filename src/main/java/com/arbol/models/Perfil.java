package com.arbol.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descri;

    @ManyToMany
    @JoinTable(
            name = "perfil_permiso",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    @OrderBy("orden ASC")
    private List<Permiso> permisos;

    @Transient
    private List<Permiso> menu;
}
