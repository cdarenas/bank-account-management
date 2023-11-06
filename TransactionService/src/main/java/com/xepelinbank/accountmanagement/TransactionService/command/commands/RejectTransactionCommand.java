package com.xepelinbank.accountmanagement.TransactionService.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Value;

@Value
public class RejectTransactionCommand {

	@TargetAggregateIdentifier
	private final String transactionId;
	private final String reason;

}