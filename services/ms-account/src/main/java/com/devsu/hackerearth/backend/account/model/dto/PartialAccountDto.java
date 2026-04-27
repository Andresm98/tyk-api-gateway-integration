package com.devsu.hackerearth.backend.account.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartialAccountDto {

	@JsonProperty("isActive")
	private boolean isActive;

	public PartialAccountDto() {
	}

	public PartialAccountDto(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
