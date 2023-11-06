package com.xepelinbank.accountmanagement.AccountService.command.rest;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xepelinbank.accountmanagement.AccountService.command.CreateAccountCommand;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
@CrossOrigin({ "http://localhost:3000" })
public class AccountCommandController {

	private final Environment env;
	private final CommandGateway commandGateway;

	public AccountCommandController(Environment env, CommandGateway commandGateway) {
		this.env = env;
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public String createAccount(@Valid @RequestBody CreateAccountRestModel createAccountRestModel) {
		CreateAccountCommand createAccountCommand = CreateAccountCommand.builder()
				.userName(createAccountRestModel.getUserName()).password(createAccountRestModel.getPassword())
				.firstName(createAccountRestModel.getFirstName()).lastName(createAccountRestModel.getLastName())
				.accountNumber(createAccountRestModel.getAccountNumber()).balance(createAccountRestModel.getBalance())
				.accountId(UUID.randomUUID().toString()).build();

		String returnValue = commandGateway.sendAndWait(createAccountCommand);

		return returnValue + " Server: " + env.getProperty("local.server.port");
	}

}
