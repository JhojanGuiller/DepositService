package com.jguiller.TransactionService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Transactions")
public class Transaction {
	
	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idTransaccion;
	
	private int idCuenta;	
	private String tipoTransaccion;
	private Double montoTransaccion;
	private String fechaTransaccion;
	
	public Transaction() {}

	public int getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(int idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}
	
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	
	public Double getMontoTransaccion() {
		return montoTransaccion;
	}

	public void setMontoTransaccion(Double montoTransaccion) {
		this.montoTransaccion = montoTransaccion;
	}

	public String getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(String fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public Transaction(@NotNull int idTransaccion, int idCuenta, String tipoTransaccion, Double montoTransaccion, String fechaTransaccion) {
		super();
		this.idTransaccion = idTransaccion;
		this.idCuenta = idCuenta;
		this.tipoTransaccion = tipoTransaccion;
		this.montoTransaccion = montoTransaccion;
		this.fechaTransaccion = fechaTransaccion;
	}
	
	
	
}
