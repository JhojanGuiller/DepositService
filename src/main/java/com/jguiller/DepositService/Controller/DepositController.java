package com.jguiller.DepositService.Controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jguiller.DepositService.Model.BankAccount;
import com.jguiller.DepositService.Model.Deposit;
import com.jguiller.DepositService.Repository.DepositRepository;

@RestController
public class DepositController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DepositRepository depositRepository;
	

	@GetMapping("/findAllDeposits")
	public List<Deposit> getDeposits() {
		return depositRepository.findAll();
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
	
	//Deposito
	public void madeDeposit(int idCuenta, String tipoOperacion, Double montoDeposito) {
		String url = "http://localhost:8081/updateBankAccountAmount" + "/" + idCuenta + "/" + tipoOperacion + "?monto=" + montoDeposito;
		
		//String url = "http://localhost:8081/updateBankAccountAmount/{idCuenta}/{tipoOperacion}?monto={monto}";
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("idCuenta", idCuenta);

		BankAccount updateBankAccountAmount = new BankAccount(idCuenta, 0, 0, montoDeposito, "0");
		
		restTemplate = new RestTemplate();
		restTemplate.put(url, updateBankAccountAmount, params);
		
	}
	
	@PostMapping("/makedeposit")
	public String makeDeposit(@RequestBody Deposit deposit) {
		String tipoOperacion = "Deposito";
		ResponseEntity<BankAccount> cuentaBancaria = getBankAccount(deposit.getIdCuenta());
		
		//Validar si existe una cuenta bancaria existente
		if (cuentaBancaria != null) {
			madeDeposit(deposit.getIdCuenta(), tipoOperacion, deposit.getMontoDeposito());
			depositRepository.save(deposit);
			cuentaBancaria = getBankAccount(deposit.getIdCuenta());
			return "Deposit made successfully: " + cuentaBancaria.getBody().getMontoCuenta();
			
		}else {
			
			return "Deposit made unsuccessfully: Check Bank Account";
			
		}
	}
	
}
