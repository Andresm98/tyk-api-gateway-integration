package com.devsu.hackerearth.backend.account.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = true)
public class Transaction extends Base {

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String type;
	private double amount;
	private double balance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	private Account account;
}
