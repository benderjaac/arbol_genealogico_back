package com.arbol.exceptions;

import com.arbol.util.Type;
import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {

    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException(String error) {
        super(error, httpStatus, httpStatus.name(), error);
    }

    public NotFoundException(Type type, Long id) {
        super(httpStatus.getReasonPhrase(), httpStatus, type.toString(),
                "Información no encontrada. " + type.idName() + ": " + id);
    }

    public NotFoundException(Type type, String clave) {
        super(httpStatus.getReasonPhrase(), httpStatus, type.toString(),
                "Información no encontrada. " + type.idName() + ": " + clave);
    }

    public NotFoundException(Type type) {
        super(httpStatus.getReasonPhrase(), httpStatus, type.toString(),
                "Información no encontrada. " + type + " no existe");
    }

}