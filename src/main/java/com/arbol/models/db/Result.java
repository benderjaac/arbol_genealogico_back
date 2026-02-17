package com.arbol.models.db;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Result<T> {

    private List<T> data = new ArrayList<>();

    private Pagination pagination = new Pagination();

    public Result() {
    }
    public Result(List<T> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

}