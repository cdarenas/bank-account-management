package com.xepelinbank.accountmanagement.TransactionService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.xepelinbank.accountmanagement.TransactionService.core.data.TransactionEntity;
import com.xepelinbank.accountmanagement.TransactionService.core.data.TransactionRepository;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionApprovedEvent;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionCreatedEvent;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionRejectedEvent;
import com.xepelinbank.accountmanagement.TransactionService.saga.TransactionSaga;

@Component
@ProcessingGroup("transaction-group")
public class TransactionEventHandler {

	private final TransactionRepository transactionRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionSaga.class);

	public TransactionEventHandler(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@EventHandler
	public void on(TransactionCreatedEvent event) throws Exception {
		TransactionEntity transactionEntity = new TransactionEntity();
		BeanUtils.copyProperties(event, transactionEntity);

		LOGGER.info("New transaction saved with Status: CREATED with id: " + event.getTransactionId());

		transactionRepository.save(transactionEntity);
	}

	@EventHandler
	public void on(TransactionApprovedEvent transactionApprovedEvent) {
		TransactionEntity transactionEntity = transactionRepository
				.findByTransactionId(transactionApprovedEvent.getTransactionId());

		if (transactionEntity == null) {
			// TODO: Do something about it
			return;
		}

		transactionEntity.setTransactionStatus(transactionApprovedEvent.getTransactionStatus());

		LOGGER.info("Transaction saved with Status: APPROVED with id: " + transactionApprovedEvent.getTransactionId());

		transactionRepository.save(transactionEntity);
	}

	@EventHandler
	public void on(TransactionRejectedEvent transactionRejectedEvent) {
		TransactionEntity transactionEntity = transactionRepository
				.findByTransactionId(transactionRejectedEvent.getTransactionId());
		transactionEntity.setTransactionStatus(transactionRejectedEvent.getTransactionStatus());

		LOGGER.info("Transaction saved with Status: REJECTED with id: " + transactionRejectedEvent.getTransactionId());

		transactionRepository.save(transactionEntity);
	}

}
