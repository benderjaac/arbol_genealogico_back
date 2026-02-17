package com.arbol.exceptions;

import com.arbol.models.response.HttpError;
import org.springframework.http.HttpStatus;
import java.util.HashMap;

public class AppException extends RuntimeException {

    private HttpError error;

    public AppException(String message, HttpStatus status, String title, String error, Object result) {
        super(message);
        this.error = new HttpError(title, error, "indefinido", status.value(), result);
    }

    public AppException(String message, HttpStatus status, String title, String error) {
        this(message, status, title, error, new HashMap<>());
    }

    public AppException(String message, HttpStatus status, String title) {
        this(message, status, title, status.getReasonPhrase());
    }

    public AppException(String message, HttpStatus status) {
        this(message, status, status.name());
    }

    public AppException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpError getHttpError(String requestURI) {
        return new HttpError(error.getTitle(), error.getError(), requestURI, error.getStatus(), error.getResult());
    }

}
