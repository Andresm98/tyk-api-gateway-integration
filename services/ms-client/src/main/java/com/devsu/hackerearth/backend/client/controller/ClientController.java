package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;

import com.devsu.hackerearth.backend.client.service.ClientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/clients")
@Slf4j
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		return ResponseEntity.ok(clientService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {

		ClientDto client = clientService.getById(id);

		if (client == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(client);
	}

	@PostMapping
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
		return new ResponseEntity<>(clientService.create(clientDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		if (id < 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		clientDto.setId(id);
		ClientDto updated = clientService.update(clientDto);

		if (updated == null) {
			return ResponseEntity.ok(clientDto);
		}

		return ResponseEntity.ok(updated);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {

		ClientDto updated = clientService.getById(id);

		if (updated == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(clientService.partialUpdate(id, partialClientDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		ClientDto client = clientService.getById(id);

		if (client == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		clientService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/notifications")
	public ResponseEntity<Void> receiveNotification(@RequestBody Double amount) {

		// Log estructurado
		log.info("### NOTIFICACIÓN RECIBIDA ###");
		log.info("Procesando alerta para movimiento de: ${}", amount);

		return ResponseEntity.ok().build();
	}
}