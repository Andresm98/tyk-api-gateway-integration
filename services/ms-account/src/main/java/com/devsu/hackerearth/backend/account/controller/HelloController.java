package com.devsu.hackerearth.backend.account.controller;

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
                        "message", "ms-account is up and running 8000",
                        "context", "api"));
    }
}