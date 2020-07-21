package com.jguiller.TransactionService.Controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jguiller.TransactionService.Model.Transaction;
import com.jguiller.TransactionService.Service.TransactionService;

import reactor.core.publisher.Mono;

@WebFluxTest
public class TransactionControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private TransactionService transactionService;
	
	@Test
	public void getAllTransactionsTest() {
		
		webTestClient
		.get()
		.uri("/transactions")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(Transaction.class);
		
	}
	
	@Test
	public void getTransactionByIdTest() {
		
		Integer id = 1;
		
		Mockito
		.when(this.transactionService.getTransactionById(id))
		.thenReturn(Mono.just(new Transaction(1, 1, "Deposito", 15.0, "21/07/2020")));
		
		this.webTestClient
		.get()
		.uri("/transactions/{idTransaccion}", id)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$..idTransaccion").isEqualTo(1)
		.jsonPath("$..idCuenta").isEqualTo(1)
		.jsonPath("$..tipoTransaccion").isEqualTo("Deposito")
		.jsonPath("$..montoTransaccion").isEqualTo(15.0)
		.jsonPath("$..fechaTransaccion").isEqualTo("21/07/2020");
		
	}

	@Test
	public void updateTransactionTest() {
		
		Transaction transaction1 = new Transaction(1, 1, "Deposito", 15.0, "21/07/2020");
		Transaction transaction2 = new Transaction(1, 1, "Deposito", 20.0, "21/07/2020");
		
		webTestClient
		.put()
		.uri("/transactions/updateTransaction/{idTransaccion}", transaction1.getIdTransaccion())
		.body(Mono.just(transaction2), Transaction.class)
		.exchange()
		.expectStatus().isOk();
		
	}
	
	@Test
	public void deleteTransactionTest() {
		Integer id = 1;
		
		webTestClient
		.delete()
		.uri("/transactions//deleteTransaction/{idTransaccion}", id)
		.exchange()
		.expectStatus().isOk();
	}

}
