package ru.otus.exception;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends GlobalAppException {

    public ClientNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
