package ru.otus.controller.data;

import java.util.List;

public record ClientRequest(
        String name,
        String address,
        List<PhoneRequest> phones
) {
}
