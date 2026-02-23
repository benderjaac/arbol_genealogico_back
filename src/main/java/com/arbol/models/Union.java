package com.arbol.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "unions")
@Getter
@Setter
public class Union {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person1_id", nullable = false)
    private Person person1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person2_id", nullable = false)
    private Person person2;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private String tipo; // matrimonio, union libre

    @OneToMany(mappedBy = "union", cascade = CascadeType.ALL)
    private List<UnionChild> children;
}
