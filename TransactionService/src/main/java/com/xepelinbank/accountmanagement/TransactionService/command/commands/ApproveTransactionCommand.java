package com.xepelinbank.accountmanagement.TransactionService.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproveTransactionCommand {

	@TargetAggregateIdentifier
	private final String transactionId;

}
