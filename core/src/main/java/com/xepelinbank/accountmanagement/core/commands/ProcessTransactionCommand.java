package com.xepelinbank.accountmanagement.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessTransactionCommand {

	@TargetAggregateIdentifier
	private final String accountId;
	private final String transactionId;
	private final Float amount;
	private final String transactionType;

}
