package ru.otus.controller.data;

import lombok.Builder;

@Builder
public record AddressResponse(
        long id,
        String street
) {
}
