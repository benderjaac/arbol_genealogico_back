package com.arbol.models.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
public class HttpOk {

    private String title;
    private String message;
    private Object result;
    private Integer status = 200;
    private LocalDateTime timestamp = LocalDateTime.now();

    public HttpOk(String title, String message, Object result) {
        this.title = title;
        this.message = message;
        this.result = result;
    }

    public HttpOk(String title, String message) {
        this(title, message, new HashMap<>());
    }

    public void statusCreated() {
        this.status = 201;
    }

}
