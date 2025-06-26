package com.typeface.dm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, Exception e) {
        super(message, e);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
