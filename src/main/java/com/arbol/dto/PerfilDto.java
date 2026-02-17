package com.arbol.dto;

import com.arbol.models.Perfil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PerfilDto {
    private Long id;
    private String nombre;
    private String descri;

    //private List<PermisoDTO> permisos;
    private List<PermisoDto> menu;

    public PerfilDto(Perfil perfil) {
        this.id = perfil.getId();
        this.nombre = perfil.getNombre();
        this.descri = perfil.getDescri();


        if (perfil.getMenu() != null) {
            this.menu = perfil.getMenu().stream()
                    .map(PermisoDto::new)
                    .collect(Collectors.toList());
        }
    }

    public PerfilDto() {
    }
}
