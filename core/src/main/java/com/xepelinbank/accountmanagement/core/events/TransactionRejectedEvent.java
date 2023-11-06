package com.xepelinbank.accountmanagement.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRejectedEvent {

	private final String transactionId;
	private final String rejectedMessage;
}
