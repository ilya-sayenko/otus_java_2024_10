package ru.otus.homework.exception;

public class IncorrectCashValueException extends RuntimeException {

    public IncorrectCashValueException(String message) {
        super(message);
    }
}
