package ru.otus.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalAppException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GlobalAppException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
