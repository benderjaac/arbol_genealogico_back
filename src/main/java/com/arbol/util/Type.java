package com.arbol.util;

public enum Type {

    USUARIO("Usuario", "Id Usuario"),
    GASTO("Gasto", "Id Gasto"),
    MOVIMIENTO("Movimiento", "Id Movimiento"),
    INGRESO("Ingreso", "Id Ingreso"),
    CATEGORIAGASTO("Categoria de gasto", "Id Categoria"),
    CATEGORIAINGRESO("Categoria de ingreso", "Id Categoria"),
    AHORRO("Ahorro", "Id Ahorro"),
    AHORRODEPOSITO("Deposito", "Id Deposito"),
    BALANCE_USUARIO("Balance", "Id Balance"),
    ESTADISTICA("Estadisticas", "Id estadistica"),
    ;

    private String display;
    private String idname;

    Type(String display, String idname) {
        this.display = display;
        this.idname = idname;
    }

    public String idName() {
        return idname;
    }

    @Override
    public String toString() {
        return this.display;
    }

}