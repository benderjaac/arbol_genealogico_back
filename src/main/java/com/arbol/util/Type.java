package com.arbol.util;

public enum Type {

    USUARIO("Usuario", "Id Usuario"),
    PERSON("Persona", "Id Persona"),
    UNION("Union", "Id Union"),
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