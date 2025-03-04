package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.exception.ClientNotFoundException;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;
import ru.otus.sessionmanager.TransactionManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final TransactionManager transactionManager;

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> clientRepository.save(client));
    }

    @Override
    public Client getClient(long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(String.format("Client with id=%d not found", id)));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
