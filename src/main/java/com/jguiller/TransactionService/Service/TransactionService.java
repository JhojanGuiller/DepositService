package com.jguiller.TransactionService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jguiller.TransactionService.Model.Transaction;
import com.jguiller.TransactionService.Repository.TransactionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	// OBTENER TODAS LAS TRANSACCIONES
	public Flux<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}
	
	// REALIZAR UNA TRANSACCION
	public Mono<Transaction> createTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}
	
	// OBTENER UNA TRANSACCION POR ID
	public Mono<Transaction> getTransactionById(Integer id) {
		return transactionRepository.findById(id);
	}
	
	// ACTUALIZAR UNA TRANSACCION
	public Mono<Transaction> updateTransaction(Transaction transaction, Integer id) {
		return transactionRepository.findById(id).flatMap(transac -> {
			transac.setIdCuenta(transaction.getIdCuenta());
			transac.setTipoTransaccion(transaction.getTipoTransaccion());
			transac.setMontoTransaccion(transaction.getMontoTransaccion());
			transac.setFechaTransaccion(transaction.getFechaTransaccion());
			return transactionRepository.save(transac);
		});
	}
	
	// ELIMINAR UNA TRANSACCION
	public Mono<Void> deleteTransaction(Integer id) {
		return transactionRepository.deleteById(id);
	}
}
