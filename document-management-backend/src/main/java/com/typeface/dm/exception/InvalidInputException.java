package com.typeface.dm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message, Exception e) {
        super(message,e);
    }

    public InvalidInputException(String message) {
        super(message);
    }
}
