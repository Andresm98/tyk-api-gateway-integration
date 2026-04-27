package com.devsu.hackerearth.backend.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Client extends Person {
	private String password;
	private boolean isActive;
}