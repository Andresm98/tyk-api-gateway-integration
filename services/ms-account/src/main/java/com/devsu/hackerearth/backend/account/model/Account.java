package com.devsu.hackerearth.backend.account.model;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = true)
public class Account extends Base {

    @Column(unique = true, nullable = false)
    private String number;
    private String type;
    private double initialAmount;
    private boolean active;

    // referencia al cliente del otro microservicio
    private Long clientId;
}
