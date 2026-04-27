package com.devsu.hackerearth.backend.account.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;
	private String number;
	private String type;
	private double initialAmount;

	@JsonProperty("isActive")
	private boolean active;

	private Long clientId;

}
