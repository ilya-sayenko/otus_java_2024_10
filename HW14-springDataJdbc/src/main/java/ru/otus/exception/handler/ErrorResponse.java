package ru.otus.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String error;
}
