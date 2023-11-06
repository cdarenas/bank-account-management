package com.xepelinbank.accountmanagement.TransactionService.core.events;

import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;

import lombok.Value;

@Value
public class TransactionApprovedEvent {

	private final String transactionId;
	private final TransactionStatus transactionStatus = TransactionStatus.APPROVED;
}
