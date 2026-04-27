package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;
import com.devsu.hackerearth.backend.client.model.Client;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll().stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		return clientRepository.findById(id)
				.map(this::mapToDto)
				.orElse(null);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = new Client();
		mapDtoToEntity(clientDto, client);
		if (clientDto.getId() != null) {
			client.setId(clientDto.getId());
		}
		return mapToDto(clientRepository.save(client));
	}

	@Override
	public ClientDto update(ClientDto clientDto) {

		log.info(">>> Mostrar Client Dto desde Service: {}", clientDto);

		return clientRepository.findById(clientDto.getId())
				.map(client -> {
					client.setDni(clientDto.getDni());
					client.setName(clientDto.getName());
					client.setPassword(clientDto.getPassword());
					client.setGender(clientDto.getGender());
					client.setAge(clientDto.getAge());
					client.setAddress(clientDto.getAddress());
					client.setPhone(clientDto.getPhone());
					if (clientDto.getIsActive() != null) {
						client.setActive(clientDto.getIsActive());
					}
					return mapToDto(clientRepository.save(client));
				}).orElse(null);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = clientRepository.findById(id)
				.orElse(null);

		client.setActive(partialClientDto.isIsActive());

		return mapToDto(clientRepository.save(client));
	}

	@Override
	public void deleteById(Long id) {

		Client client = clientRepository.findById(id)
				.orElse(null);

		client.setActive(false);
		clientRepository.save(client);
	}

	private ClientDto mapToDto(Client c) {
		ClientDto dto = new ClientDto();
		dto.setId(c.getId());
		dto.setDni(c.getDni());
		dto.setName(c.getName());
		dto.setPassword(c.getPassword());
		dto.setGender(c.getGender());
		dto.setAge(c.getAge());
		dto.setAddress(c.getAddress());
		dto.setPhone(c.getPhone());
		dto.setIsActive(c.isActive());

		return dto;
	}

	private void mapDtoToEntity(ClientDto d, Client e) {
		e.setName(d.getName());
		e.setDni(d.getDni());
		e.setGender(d.getGender());
		e.setAge(d.getAge());
		e.setAddress(d.getAddress());
		e.setPhone(d.getPhone());
		e.setPassword(d.getPassword());
		if (d.getIsActive() != null) {
			e.setActive(d.getIsActive());
		}
	}
}