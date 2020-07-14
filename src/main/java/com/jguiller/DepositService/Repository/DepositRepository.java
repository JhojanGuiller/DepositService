package com.jguiller.DepositService.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jguiller.DepositService.Model.Deposit;

@Repository
public interface DepositRepository extends MongoRepository<Deposit, Integer>{

}
