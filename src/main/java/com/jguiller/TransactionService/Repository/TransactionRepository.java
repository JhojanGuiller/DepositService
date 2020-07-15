package com.jguiller.TransactionService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.TransactionService.Model.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, Integer>{

}
