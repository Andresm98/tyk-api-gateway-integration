package com.devsu.hackerearth.backend.client.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(
                Map.of(
                        "message", "ms-client is up and running 8001",
                        "context", "api"));
    }
}