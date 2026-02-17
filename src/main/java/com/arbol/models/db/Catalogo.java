package com.arbol.models.db;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Catalogo<T> {

    private List<T> data = new ArrayList<>();

    public Catalogo() {
    }
    public Catalogo(List<T> data) {
        this.data = data;
    }
}
