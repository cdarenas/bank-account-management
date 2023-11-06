package com.xepelinbank.accountmanagement.TransactionService.core.events;

import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;

import lombok.Data;

@Data
public class TransactionCreatedEvent {

	private String transactionId;
	private String accountId;
	private String userName;
	private String transactionType;
	private Float amount;
	private TransactionStatus transactionStatus;
}
