package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public ResponseEntity<List<TransactionDto>> getAll() {
		return ResponseEntity.ok(transactionService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TransactionDto> get(@PathVariable Long id) {

		TransactionDto transaction = transactionService.getById(id);

		if (transaction == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(transaction);
	}

	@PostMapping
	public ResponseEntity<TransactionDto> create(@RequestBody TransactionDto transactionDto) {
		// F2 y F3 se procesan en el Service (Validación de saldo)
		return new ResponseEntity<>(transactionService.create(transactionDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/{clientId}")
	public ResponseEntity<TransactionDto> delete(@PathVariable Long clientId) {
		return ResponseEntity.ok(transactionService.deleteById(clientId));
	}

	// F4: Reporte de Estado de Cuenta
	@GetMapping("/clients/{clientId}/report")
	public ResponseEntity<List<BankStatementDto>> report(
			@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {

		List<BankStatementDto> statements = transactionService.getAllByAccountClientIdAndDateBetween(
				clientId, dateTransactionStart, dateTransactionEnd);

		if (statements == null) {
			statements = java.util.Collections.emptyList();
		}

		return ResponseEntity.ok(statements);
	}
}
