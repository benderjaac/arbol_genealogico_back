package com.arbol.util;

import com.arbol.models.response.HttpOk;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class Response {

    private Type type;

    public Response(Type type) {
        this.type = type;
    }

    public ResponseEntity<HttpOk> ok(String message, Object result) {
        HttpOk ok = new HttpOk(type.toString(), message, result);
        return ResponseEntity.ok(ok);
    }

    public ResponseEntity<HttpOk> ok(String message) {
        return ok(message, new HashMap<>());
    }

    public ResponseEntity<HttpOk> find(Object result) {
        return ok("Información encontrada", result);
    }

    public ResponseEntity<HttpOk> create(String id, Object result) {
        HttpOk ok = new HttpOk(type.toString(), "Información guardada. " + type.idName() + ": " + id, result);
        ok.statusCreated();
        return ResponseEntity.status(HttpStatus.CREATED).body(ok);
    }

    public ResponseEntity<HttpOk> create(String id) {
        return create(id, new HashMap<>());
    }

    public ResponseEntity<HttpOk> create(Integer id) {
        return create(String.valueOf(id), new HashMap<>());
    }

    public ResponseEntity<HttpOk> update(String id) {
        return ok("Información actualizada. " + type.idName() + ": " + id);
    }

    public ResponseEntity<HttpOk> update(String id, Object result) {
        return ok("Información actualizada. " + type.idName() + ": " + id, result);
    }

    public ResponseEntity<HttpOk> update(Integer id) {
        return update(String.valueOf(id));
    }

    public ResponseEntity<HttpOk> delete(String id, Object result) {
        return ok("Información eliminada. " + type.idName() + ": " + id, result);
    }

    public ResponseEntity<HttpOk> delete(String id) {
        return ok("Información eliminada. " + type.idName() + ": " + id);
    }
}
