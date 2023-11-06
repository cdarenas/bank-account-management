package com.xepelinbank.accountmanagement.TransactionService.core.model;

import lombok.Value;

@Value
public class TransactionSummary {

	private final String transactionId;
	private final TransactionStatus transactionStatus;
	private final String message;

}
