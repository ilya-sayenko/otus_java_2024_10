package ru.otus.dto;

import java.util.List;

public record ClientResponse(
        long id,
        String name,
        AddressResponse address,
        List<PhoneResponse> phones
) {
}
