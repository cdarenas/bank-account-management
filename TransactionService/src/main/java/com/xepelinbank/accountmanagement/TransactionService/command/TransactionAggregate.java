package com.xepelinbank.accountmanagement.TransactionService.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.xepelinbank.accountmanagement.TransactionService.command.commands.ApproveTransactionCommand;
import com.xepelinbank.accountmanagement.TransactionService.command.commands.CreateTransactionCommand;
import com.xepelinbank.accountmanagement.TransactionService.command.commands.RejectTransactionCommand;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionApprovedEvent;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionCreatedEvent;
import com.xepelinbank.accountmanagement.TransactionService.core.events.TransactionRejectedEvent;
import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;

@Aggregate
public class TransactionAggregate {

	@AggregateIdentifier
	private String transactionId;
	private String accountId;
	private String userName;
	private String transactionType;
	private Float amount;
	private TransactionStatus transactionStatus;

	public TransactionAggregate() {
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@CommandHandler
	public TransactionAggregate(CreateTransactionCommand createTransactionCommand) {
		TransactionCreatedEvent transactionCreatedEvent = new TransactionCreatedEvent();
		BeanUtils.copyProperties(createTransactionCommand, transactionCreatedEvent);

		AggregateLifecycle.apply(transactionCreatedEvent);
	}

	@EventSourcingHandler
	public void on(TransactionCreatedEvent transactionCreatedEvent) throws Exception {
		this.transactionId = transactionCreatedEvent.getTransactionId();
		this.setAccountId(transactionCreatedEvent.getAccountId());
		this.setUserName(transactionCreatedEvent.getUserName());
		this.setTransactionType(transactionCreatedEvent.getTransactionType());
		this.setAmount(transactionCreatedEvent.getAmount());
		this.setTransactionStatus(transactionCreatedEvent.getTransactionStatus());
	}

	@CommandHandler
	public void handle(ApproveTransactionCommand approveTransactionCommand) {
		TransactionApprovedEvent transactionApprovedEvent = new TransactionApprovedEvent(
				approveTransactionCommand.getTransactionId());

		AggregateLifecycle.apply(transactionApprovedEvent);
	}

	@EventSourcingHandler
	public void on(TransactionApprovedEvent transactionApprovedEvent) {
		this.setTransactionStatus(transactionApprovedEvent.getTransactionStatus());
	}

	@CommandHandler
	public void handle(RejectTransactionCommand rejectTransactionCommand) {

		TransactionRejectedEvent transactionRejectedEvent = new TransactionRejectedEvent(
				rejectTransactionCommand.getTransactionId(), rejectTransactionCommand.getReason());

		AggregateLifecycle.apply(transactionRejectedEvent);

	}

	@EventSourcingHandler
	public void on(TransactionRejectedEvent transactionRejectedEvent) {
		this.setTransactionStatus(transactionRejectedEvent.getTransactionStatus());
	}

}
