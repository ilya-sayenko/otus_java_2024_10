package ru.otus.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.otus.exception.GlobalAppException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalAppException.class)
    public ResponseEntity<ErrorResponse> handle(GlobalAppException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .error(ex.getMessage())
                                .build()
                );
    }
}
