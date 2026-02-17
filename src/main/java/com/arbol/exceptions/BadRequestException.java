package com.arbol.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends AppException {

    private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadRequestException(String error) {
        super(error, httpStatus, httpStatus.name(), error);
    }

    public BadRequestException(Map<String, String> errorsEntity) {
        super("Verificar los datos enviados", httpStatus, httpStatus.name(), "Verificar los datos enviados", errorsEntity);
    }

}
