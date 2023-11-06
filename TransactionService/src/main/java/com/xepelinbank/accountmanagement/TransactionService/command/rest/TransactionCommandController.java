package com.xepelinbank.accountmanagement.TransactionService.command.rest;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xepelinbank.accountmanagement.TransactionService.command.commands.CreateTransactionCommand;
import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@CrossOrigin({ "http://localhost:3000" })
public class TransactionCommandController {

	private final CommandGateway commandGateway;

	public TransactionCommandController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@PostMapping
	public String createTransaction(@Valid @RequestBody TransactionCreateRest transaction) {

		String transactionId = UUID.randomUUID().toString();

		CreateTransactionCommand createTransactionCommand = CreateTransactionCommand.builder()
				.transactionType(transaction.getTransactionType()).accountId(transaction.getAccountId())
				.userName(transaction.getUserName()).amount(transaction.getAmount()).transactionId(transactionId)
				.transactionStatus(TransactionStatus.CREATED).build();

		commandGateway.sendAndWait(createTransactionCommand);

		return "New Transaction with id: " + transactionId + " is in PROGRESS";
	}

}
