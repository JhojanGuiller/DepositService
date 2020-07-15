package com.jguiller.TransactionService.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jguiller.TransactionService.Model.BankAccount;
import com.jguiller.TransactionService.Model.Transaction;
import com.jguiller.TransactionService.Repository.TransactionRepository;

@RestController
public class TransactionController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TransactionRepository transactionRepository;
	

	@GetMapping("/findAllTransactions")
	public List<Transaction> getTransactions() {
		return transactionRepository.findAll();
	}
	
	//Obtener una cuenta de banco
	public ResponseEntity<BankAccount> getBankAccount(int idCuenta){
		String url = "http://localhost:8081/findBankAccount" + "/" + idCuenta;
		ResponseEntity<BankAccount> cuentaBancaria = restTemplate.getForEntity(url, BankAccount.class);
		if (cuentaBancaria.getBody() != null) {
			return cuentaBancaria;
		}else {
			return null;
		}
	}
	
	//Realizar la transaccion
	public void madeTransaction(int idCuenta, String tipoTransaccion, Double montoTransaccion) {
		String url = "http://localhost:8081/updateBankAccountAmount" + "/" + idCuenta + "/" + tipoTransaccion + "?monto=" + montoTransaccion;
		
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("idCuenta", idCuenta);

		BankAccount updateBankAccountAmount = new BankAccount(idCuenta, 0, 0, montoTransaccion, "0");
		
		restTemplate = new RestTemplate();
		restTemplate.put(url, updateBankAccountAmount, params);
		
	}
	
	@PostMapping("/makeTransaction/{tipoTransaccion}")
	public String makeTransaction(@RequestBody Transaction transaction, @PathVariable String tipoTransaccion) {
		ResponseEntity<BankAccount> cuentaBancaria = getBankAccount(transaction.getIdCuenta());
		
		//Validar si existe una cuenta bancaria existente
		if (cuentaBancaria != null) {
			madeTransaction(transaction.getIdCuenta(), tipoTransaccion, transaction.getMontoTransaccion());
			transactionRepository.save(transaction);
			cuentaBancaria = getBankAccount(transaction.getIdCuenta());
			return "Transaction made successfully: " + cuentaBancaria.getBody().getMontoCuenta();
			
		}else {
			
			return "Transaction made unsuccessfully: Check Bank Account";
			
		}
	}
	
}
