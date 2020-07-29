package com.jguiller.TransactionService.Controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.jguiller.TransactionService.Model.BankAccount;
import com.jguiller.TransactionService.Model.Transaction;
import com.jguiller.TransactionService.Service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	private TransactionService transactionService;
	
	// CALL TO BANK ACCOUNT MICROSERVICE BY IDCUENTA
	public ResponseEntity<BankAccount> getBankAccount(int idCuenta){
		String url = "http://localhost:8803/bankAccounts/" + idCuenta;
		ResponseEntity<BankAccount> cuentaBancaria = restTemplate.getForEntity(url, BankAccount.class);
		if (cuentaBancaria.getBody() != null) {
			return cuentaBancaria;
		}else {
			return null;
		}
	}
	
	public Mono<BankAccount> getBankAccountByIdCuenta (int idCuenta){
		return webClientBuilder.build()
				.get()
				.uri("http://localhost:8803/bankAccounts/" + idCuenta)
				.retrieve()
				.bodyToMono(BankAccount.class);
	}
	
	
	//Realizar la transaccion
	public void madeTransaction(int idCuenta, String tipoTransaccion, Double montoTransaccion) {
		String url = "http://localhost:8803/bankAccounts/updateBankAccountAmount" + "/" + idCuenta + "/" + tipoTransaccion + "?monto=" + montoTransaccion;
		
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("idCuenta", idCuenta);

		BankAccount updateBankAccountAmount = new BankAccount(idCuenta, 0, 0, montoTransaccion, "0");
		
		restTemplate = new RestTemplate();
		restTemplate.put(url, updateBankAccountAmount, params);
		
	}
	
	public void madeTransactionAmount(int idCuenta, String tipoTransaccion, Double montoTransaccion) {
		webClientBuilder.build()
		.put()
		.uri("http://localhost:8803/bankAccounts/updateBankAccountAmount/" + idCuenta + "/" + tipoTransaccion + "?monto=" + montoTransaccion)
		.retrieve()
		.bodyToMono(BankAccount.class);
	}
	
	// OBTENER TODAS LAS TRANSACCIONES
	@GetMapping
	public Flux<Transaction> getTransactions() {
		return transactionService.getAllTransactions();
	}
	
	// REALIZAR UNA TRANSACCION
	@PostMapping("/makeTransaction")
	public Mono<Transaction> makeTransaction(@RequestBody Transaction transaction) {
		ResponseEntity<BankAccount> cuentaBancaria = getBankAccount(transaction.getIdCuenta());
		
		//Validar si existe una cuenta bancaria existente
		if (cuentaBancaria != null) {
			
			madeTransaction(transaction.getIdCuenta(), transaction.getTipoTransaccion(), transaction.getMontoTransaccion());
			return transactionService.createTransaction(transaction);
			
		}else {
			
			return null;
			
		}
	}
	
	// TEST MAKE TRANSACTION WITH WEBCLIENT
	@PostMapping("/testmake")
	public Mono<Transaction> testmake(@RequestBody Transaction transaction) {
		Mono<BankAccount> cuentaBancaria = getBankAccountByIdCuenta(transaction.getIdCuenta());
		
		//Validar si existe una cuenta bancaria existente
		if (cuentaBancaria != null) {
			
			madeTransactionAmount(transaction.getIdCuenta(), transaction.getTipoTransaccion(), transaction.getMontoTransaccion());
			return transactionService.createTransaction(transaction);
			
		}else {
			
			return null;
			
		}
	} 
	
	// OBTENER UNA TRANSACCION POR ID
	@GetMapping("/{idTransaccion}")
	public Mono<Transaction> getTransactionById(@PathVariable(value = "idTransaccion") int id) {
		return transactionService.getTransactionById(id);
	}

	// ACTUALIZAR UNA TRANSACCION
	@PutMapping("/updateTransaction/{idTransaccion}")
	public Mono<Transaction> updateTransaction(@RequestBody Transaction transaction, @PathVariable(value = "idTransaccion") int id) {
		return transactionService.updateTransaction(transaction, id);
	}
	
	// ELIMINAR UNA TRANSACCION
	@DeleteMapping("/deleteTransaction/{idTransaccion}")
	public Mono<Void> deleteTransaction(@PathVariable(value = "idTransaccion") int id) {
		return transactionService.deleteTransaction(id);
	}
	
}
