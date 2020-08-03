package com.jguiller.TransactionService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Transactions")
@Data @NoArgsConstructor @AllArgsConstructor
public class Transaction {
	
	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idTransaccion;
	
	private int idCuenta;	
	private String tipoTransaccion;
	private Double montoTransaccion;
	private String fechaTransaccion;
	
}
