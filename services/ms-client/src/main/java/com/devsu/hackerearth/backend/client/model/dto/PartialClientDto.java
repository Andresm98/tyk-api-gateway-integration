package com.devsu.hackerearth.backend.client.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartialClientDto {

    @JsonProperty("isActive")
    private boolean isActive;


    public PartialClientDto() {
    }

    public PartialClientDto(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}