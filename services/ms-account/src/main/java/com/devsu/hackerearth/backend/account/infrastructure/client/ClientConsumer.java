package com.devsu.hackerearth.backend.account.infrastructure.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.devsu.hackerearth.backend.account.model.dto.ExternalClientDto;

@Component
public class ClientConsumer {

    private final RestTemplate restTemplate;
    private final String CLIENT_SERVICE_URL = "http://localhost:8001/api/clients/";

    public ClientConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getClientName(Long clientId) {
        try {
            // invocar el microservicio *(client)
            ExternalClientDto client = restTemplate.getForObject(CLIENT_SERVICE_URL + clientId,
                    ExternalClientDto.class);
            return client != null ? client.getName() : "Usuario Desconocido";
        } catch (Exception e) {
            return "Error al obtener nombre";
        }
    }
}
