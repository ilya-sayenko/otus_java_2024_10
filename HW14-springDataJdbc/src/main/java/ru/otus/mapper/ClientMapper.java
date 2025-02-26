package ru.otus.mapper;

import org.springframework.stereotype.Component;
import ru.otus.controller.data.AddressResponse;
import ru.otus.controller.data.ClientRequest;
import ru.otus.controller.data.ClientResponse;
import ru.otus.controller.data.PhoneResponse;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.util.HashSet;
import java.util.List;

@Component
public class ClientMapper {

    public Client fromRequest(ClientRequest clientRequest) {
        List<Phone> phones = clientRequest.phones().stream()
                .map(rqPhone -> Phone.builder().number(rqPhone.number()).build())
                .toList();

        return Client.builder()
                .name(clientRequest.name())
                .phones(new HashSet<>(phones))
                .address(Address.builder().street(clientRequest.address()).build())
                .build();
    }

    public ClientResponse toResponse(Client client) {
        Address address = client.address();
        List<PhoneResponse> phoneResponses = client.phones().stream()
                .map(phone -> PhoneResponse.builder()
                        .id(phone.id())
                        .number(phone.number())
                        .build())
                .toList();

        return ClientResponse.builder()
                .id(client.id())
                .name(client.name())
                .address(AddressResponse.builder()
                        .id(address.id())
                        .street(address.street())
                        .build())
                .phones(phoneResponses)
                .build();
    }

    public List<ClientResponse> toResponses(List<Client> client) {
        return client.stream()
                .map(this::toResponse)
                .toList();
    }
}
