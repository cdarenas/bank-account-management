package com.xepelinbank.accountmanagement.TransactionService.saga;

import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xepelinbank.accountmanagement.TransactionService.command.commands.ApproveTransactionCommand;
import com.xepelinbank.accountmanagement.TransactionService.command.commands.RejectTransactionCommand;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionCreatedEvent;
import com.xepelinbank.accountmanagement.core.commands.ProcessTransactionCommand;
import com.xepelinbank.accountmanagement.core.events.TransactionCompletedEvent;

@Saga
public class TransactionSaga {

	@Autowired
	private transient CommandGateway commandGateway;

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionSaga.class);

	@StartSaga
	@SagaEventHandler(associationProperty = "transactionId")
	public void handle(TransactionCreatedEvent transactionCreatedEvent) {

		ProcessTransactionCommand processTransactionCommand = ProcessTransactionCommand.builder()
				.transactionId(transactionCreatedEvent.getTransactionId())
				.accountId(transactionCreatedEvent.getAccountId()).amount(transactionCreatedEvent.getAmount())
				.transactionType(transactionCreatedEvent.getTransactionType()).build();

		LOGGER.info("TransactionCreatedEvent handled for transactionId: " + processTransactionCommand.getTransactionId()
				+ " and accountId: " + processTransactionCommand.getAccountId());

		commandGateway.send(processTransactionCommand, new CommandCallback<ProcessTransactionCommand, Object>() {

			@Override
			public void onResult(CommandMessage<? extends ProcessTransactionCommand> commandMessage,
					CommandResultMessage<? extends Object> commandResultMessage) {
				if (commandResultMessage.isExceptional()) {
					// Start a compensating transaction
					RejectTransactionCommand rejectOrderCommand = new RejectTransactionCommand(
							transactionCreatedEvent.getTransactionId(),
							commandResultMessage.exceptionResult().getMessage());

					commandGateway.send(rejectOrderCommand);
				}
			}
		});

	}

	@EndSaga
	@SagaEventHandler(associationProperty = "transactionId")
	public void handle(TransactionCompletedEvent transactionCompletedEvent) {

		LOGGER.info("TransactionCompletedEvent is called for accountId: " + transactionCompletedEvent.getAccountId()
				+ " and transactionId: " + transactionCompletedEvent.getTransactionId());

		// Send an ApproveOrderCommand
		ApproveTransactionCommand approveTransactionCommand = new ApproveTransactionCommand(
				transactionCompletedEvent.getTransactionId());

		commandGateway.send(approveTransactionCommand);
	}

}
