package com.xepelinbank.accountmanagement.TransactionService.core.events;

import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;

import lombok.Value;

@Value
public class TransactionRejectedEvent {

	private final String transactionId;
	private final String reason;
	private final TransactionStatus transactionStatus = TransactionStatus.REJECTED;
}
