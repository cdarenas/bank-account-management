package com.xepelinbank.accountmanagement.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionCompletedEvent {

	private final String accountId;
	private final Float amount;
	private final String transactionId;
	private final String transactionType;

}
