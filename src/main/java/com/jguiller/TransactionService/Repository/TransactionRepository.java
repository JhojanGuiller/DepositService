package com.jguiller.TransactionService.Repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.TransactionService.Model.Transaction;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, Integer>{
	
}
