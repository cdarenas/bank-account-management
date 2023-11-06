package com.xepelinbank.accountmanagement.TransactionService.command.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionCreateRest {
	@NotBlank(message = "El id de la cuenta es requerido")
	private String accountId;

	@NotBlank(message = "El nombre de usuario de la cuenta es requerido")
	private String userName;

	private Float amount;

	// DEPOSIT OR WITHDRAW
	@NotBlank(message = "El tipo de transaccion es requerido")
	private String transactionType;

}
