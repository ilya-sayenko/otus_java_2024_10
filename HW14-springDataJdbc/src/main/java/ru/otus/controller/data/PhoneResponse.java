package ru.otus.controller.data;

import lombok.Builder;

@Builder
public record PhoneResponse(
        long id,
        String number
) {
}
