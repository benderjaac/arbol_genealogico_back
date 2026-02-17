package com.arbol.services;

import com.arbol.models.Perfil;
import com.arbol.models.Permiso;
import com.arbol.repositories.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    public Optional<Perfil> getPerfilById(Long id) {
        return perfilRepository.findById(id);
    }

    public List<Permiso> getMenuEstructuradoPorPerfil(Long perfilId) {
        // Consulta permisos visibles directamente del repositorio
        Optional<Perfil> perfil = perfilRepository.findById(perfilId);
        List<Permiso> permisosVisibles = perfil.get().getPermisos();

        // Mapeo para construir jerarquía
        Map<Long, Permiso> map = new HashMap<>();
        for (Permiso permiso : permisosVisibles) {
            permiso.setHijos(new ArrayList<>()); // evitar datos residuales
            map.put(permiso.getId(), permiso);
        }

        List<Permiso> menuRaiz = new ArrayList<>();

        for (Permiso permiso : permisosVisibles) {
            if (permiso.getPadre() != null) {
                Permiso padre = map.get(permiso.getPadre().getId());
                if (padre != null) {
                    padre.getHijos().add(permiso);
                }
            } else {
                menuRaiz.add(permiso);
            }
        }

        return menuRaiz;
    }
}
