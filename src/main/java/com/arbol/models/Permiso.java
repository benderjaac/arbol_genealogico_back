package com.arbol.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rol;
    private String nombre;
    private boolean visible;
    private String descri;
    private String link;
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "padre_id")
    private Permiso padre;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Permiso> hijos = new ArrayList<>();

    @ManyToMany(mappedBy = "permisos")
    private Set<Perfil> perfiles = new HashSet<>();

    private Integer orden;
}
