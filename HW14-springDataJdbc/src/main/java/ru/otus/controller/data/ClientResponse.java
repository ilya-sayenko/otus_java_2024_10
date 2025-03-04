package ru.otus.controller.data;

import lombok.Builder;

import java.util.List;

@Builder
public record ClientResponse(
        long id,
        String name,
        AddressResponse address,
        List<PhoneResponse> phones
) {
}
