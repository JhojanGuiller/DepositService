package com.jguiller.DepositService.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Deposits")
public class Deposit {
	
	@Id
	@NotNull
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int idDeposit;
	
	private int idCuenta;	
	private Double montoDeposito;
	private String fechaDeposito;
	
	public Deposit() {}

	public int getIdDeposit() {
		return idDeposit;
	}

	public void setIdDeposit(int idDeposit) {
		this.idDeposit = idDeposit;
	}

	public int getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(int idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Double getMontoDeposito() {
		return montoDeposito;
	}

	public void setMontoDeposito(Double montoDeposito) {
		this.montoDeposito = montoDeposito;
	}

	public String getFechaDeposito() {
		return fechaDeposito;
	}

	public void setFechaDeposito(String fechaDeposito) {
		this.fechaDeposito = fechaDeposito;
	}

	public Deposit(@NotNull int idDeposit, int idCuenta, Double montoDeposito, String fechaDeposito) {
		super();
		this.idDeposit = idDeposit;
		this.idCuenta = idCuenta;
		this.montoDeposito = montoDeposito;
		this.fechaDeposito = fechaDeposito;
	}
	
	
	
}
