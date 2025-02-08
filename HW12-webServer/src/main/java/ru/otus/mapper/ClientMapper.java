package ru.otus.mapper;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.dto.AddressResponse;
import ru.otus.dto.ClientRequest;
import ru.otus.dto.ClientResponse;
import ru.otus.dto.PhoneResponse;

import java.util.List;

public class ClientMapper {

    public static Client fromRequest(ClientRequest clientRequest) {
        List<Phone> phones = clientRequest.phones().stream()
                .map(rqPhone -> new Phone(null, rqPhone.number()))
                .toList();

        return new Client(null, clientRequest.name(), new Address(null, clientRequest.address()) , phones);
    }

    public static ClientResponse toResponse(Client client) {
        Address address = client.getAddress();
        List<PhoneResponse> phoneResponses = client.getPhones().stream()
                .map(phone -> new PhoneResponse(phone.getId(), phone.getNumber()))
                .toList();

        return new ClientResponse(
                client.getId(),
                client.getName(),
                new AddressResponse(address.getId(), address.getStreet()),
                phoneResponses
        );
    }

    public static List<ClientResponse> toResponses(List<Client> client) {
        return client.stream()
                .map(ClientMapper::toResponse)
                .toList();
    }
}
