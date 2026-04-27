package com.devsu.hackerearth.backend.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = true)
public class Person extends Base {

	private String name;
	private String dni;
	private String gender;
	private Integer age;
	private String address;
	private String phone;
}
