package com.jguiller.TransactionService.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.TransactionService.Model.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, Integer>{
	Optional<Transaction> getByIdTransaccion(Integer idTransaccion);

	void deleteByIdTransaccion(int idTransaccion);
}
