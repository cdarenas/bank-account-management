package com.xepelinbank.accountmanagement.AccountService.command.rest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAccountRestModel {

	@NotBlank(message = "El nombre de usuario es requerido")
	private String userName;
	@NotBlank(message = "El password es requerido")
	private String password;
	private String firstName;
	private String lastName;
	@NotBlank(message = "El numero de cuenta es requerido")
	private String accountNumber;
	private Float balance;
}
