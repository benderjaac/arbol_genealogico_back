package com.arbol.dto;

import com.arbol.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSimpleDto {
    private Long id;
    private String username;
    private String email;
    private Long perfil_id;
    private String perfilNombre;


    public UserSimpleDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.perfil_id=user.getPerfil().getId();
        this.perfilNombre = user.getPerfil() != null ? user.getPerfil().getNombre() : null;
    }
}
