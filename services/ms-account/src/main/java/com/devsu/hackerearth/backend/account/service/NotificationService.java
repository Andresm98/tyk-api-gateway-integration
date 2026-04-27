package com.devsu.hackerearth.backend.account.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

    private final RestTemplate restTemplate;

    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void notifyClientService(Long clientId, double amount) {
        try {
            restTemplate.postForEntity("http://localhost:8001/api/clients/notifications", amount, Void.class);
            log.info("Notificación asíncrona enviada para el cliente:: ${}", clientId);
        } catch (Exception e) {
            log.info("Error en notificación asíncrona:: ${}", e.getMessage());
        }
    }

}
