package com.xepelinbank.accountmanagement.TransactionService.command.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;

@Builder
@Data
public class CreateTransactionCommand {

	@TargetAggregateIdentifier
	public final String transactionId;

	private final String userName;
	private final String accountId;
	private final Float amount;
	private final String transactionType;
	private final TransactionStatus transactionStatus;

}
