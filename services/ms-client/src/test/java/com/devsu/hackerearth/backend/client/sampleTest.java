package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class sampleTest {

    @Autowired
    private TestRestTemplate restTemplate;

    // --- PRUEBA ORIGINAL (MOCK) ---
    @Test
    void createClientTest() {
        ClientService clientServiceMock = mock(ClientService.class);
        ClientController clientController = new ClientController(clientServiceMock);

        ClientDto newClient = new ClientDto(1L, "Dni", "Name", "Password", "Gender", 1, "Address", "9999999999", true);
        when(clientServiceMock.create(any())).thenReturn(newClient);

        ResponseEntity<ClientDto> response = clientController.create(newClient);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    //
    @Test
    void clientEntityTest() {
        Client client = new Client();
        client.setName("Santiago Moreta");
        client.setActive(true);
        assertEquals("Santiago Moreta", client.getName());
        assertTrue(client.isActive());
    }

    @Test
    public void createClientAndGetById_ShouldReturnClient() {
        ClientDto newClient = new ClientDto();
        newClient.setDni("1755667788");
        newClient.setName("Test Integration");
        newClient.setPassword("pass123");
        newClient.setIsActive(true);

        ResponseEntity<ClientDto> postResponse = restTemplate.postForEntity("/api/clients", newClient, ClientDto.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Long id = postResponse.getBody().getId();
        ResponseEntity<ClientDto> getResponse = restTemplate.getForEntity("/api/clients/" + id, ClientDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}