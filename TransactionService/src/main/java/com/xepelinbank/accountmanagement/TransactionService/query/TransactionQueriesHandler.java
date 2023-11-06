package com.xepelinbank.accountmanagement.TransactionService.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.xepelinbank.accountmanagement.TransactionService.core.data.TransactionEntity;
import com.xepelinbank.accountmanagement.TransactionService.core.data.TransactionRepository;
import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionSummary;

@Component
public class TransactionQueriesHandler {

	TransactionRepository transactionRepository;

	public TransactionQueriesHandler(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@QueryHandler
	public TransactionSummary findOrder(FindTransactionQuery findTransactionQuery) {
		TransactionEntity transactionEntity = transactionRepository
				.findByTransactionId(findTransactionQuery.getTransactionId());
		return new TransactionSummary(transactionEntity.getTransactionId(), transactionEntity.getTransactionStatus(),
				"");
	}

}
