package com.arbol.models.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;

@Getter
@Setter
public class HttpError {

    private String title;
    private String error;
    private String path;
    private Integer status;
    private Object result;
    private LocalDateTime timestamp = LocalDateTime.now();

    public HttpError(String title, String error, String path, Integer status, Object result) {
        this.title = title;
        this.error = error;
        this.path = path;
        this.status = status;
        this.result = result;
    }

    public HttpError(String title, String error, String path, Integer status) {
        this(title, error, path, status, new HashMap<>());
    }

}
