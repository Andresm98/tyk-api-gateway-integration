package com.devsu.hackerearth.backend.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class mainTest {

	@Autowired
	private TestRestTemplate restTemplate;

	// F6: Prueba de Integración - Flujo básico de creación
	@Test
	void createAccountIntegrationTest() {
		AccountDto account = new AccountDto(null, "111222", "Corriente", 500.0, true, 1L);
		ResponseEntity<AccountDto> response = restTemplate.postForEntity("/api/accounts", account, AccountDto.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Assertions.assertThat(response.getBody().getNumber()).isEqualTo("111222");
	}

	// F6: Prueba de Integración - Validación de Saldo Insuficiente (Requisito F3)
	@Test
	void whenWithdrawMoreThanBalance_thenReturns400AndErrorMessage() {
		AccountDto newAccount = new AccountDto(null, "TEST-999", "Ahorros", 100.0, true, 1L);
		ResponseEntity<AccountDto> accountRes = restTemplate.postForEntity("/api/accounts", newAccount, AccountDto.class);
		Long accountId = accountRes.getBody().getId();

		TransactionDto transaction = new TransactionDto();
		transaction.setAccountId(accountId);
		transaction.setAmount(-500.0);

		ResponseEntity<Map> response = restTemplate.postForEntity("/api/transactions", transaction, Map.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals("Saldo no disponible", response.getBody().get("message"));
	}
}