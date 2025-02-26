package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.controller.data.ClientRequest;
import ru.otus.controller.data.ClientResponse;
import ru.otus.mapper.ClientMapper;
import ru.otus.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @GetMapping
    public List<ClientResponse> getAllClients() {
        return clientMapper.toResponses(clientService.findAll());
    }

    @PostMapping
    public ClientResponse createClient(@RequestBody ClientRequest clientRequest) {
        return clientMapper.toResponse(
                clientService.saveClient(clientMapper.fromRequest(clientRequest))
        );
    }

    @GetMapping("/{id}")
    public ClientResponse getClient(@PathVariable Long id) {
        return clientMapper.toResponse(clientService.getClient(id));
    }
}
