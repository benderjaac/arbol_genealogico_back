package com.arbol.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResponseApi<T> {
    private String title;
    private String status;
    private String message;
    private T data;
    private Date timestamp;

    public ResponseApi() {}

    public ResponseApi(String title, String status, String message, T data, Date timestamp) {
        this.title = title;
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }
}
