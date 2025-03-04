package ru.otus.service;

import ru.otus.model.Client;

import java.util.List;

public interface ClientService {

    Client saveClient(Client client);

    Client getClient(long id);

    List<Client> findAll();
}
