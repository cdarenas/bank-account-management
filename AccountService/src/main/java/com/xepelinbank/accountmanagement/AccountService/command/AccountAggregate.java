package com.xepelinbank.accountmanagement.AccountService.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.xepelinbank.accountmanagement.AccountService.core.events.AccountCreatedEvent;
import com.xepelinbank.accountmanagement.core.commands.ProcessTransactionCommand;
import com.xepelinbank.accountmanagement.core.events.TransactionCompletedEvent;

@Aggregate
public class AccountAggregate {

	@AggregateIdentifier
	private String accountId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String accountNumber;
	private Float balance;

	public AccountAggregate() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	@CommandHandler
	public AccountAggregate(CreateAccountCommand createAccountCommand) {

		if (createAccountCommand.getBalance() <= 0f) {
			throw new IllegalArgumentException("El saldo inicial no puede ser menor o igual a cero");
		}

		if (createAccountCommand.getAccountNumber() == null || createAccountCommand.getAccountNumber().isBlank()) {
			throw new IllegalArgumentException("El numero de cuenta no puede ser nulo");
		}

		AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
		BeanUtils.copyProperties(createAccountCommand, accountCreatedEvent);

		AggregateLifecycle.apply(accountCreatedEvent);
	}

	@CommandHandler
	public void handle(ProcessTransactionCommand processTransactionCommand) {
		if (processTransactionCommand.getTransactionType() == "WITHDRAW") {
			if (balance < processTransactionCommand.getAmount()) {
				throw new IllegalArgumentException("No posee dinero suficiente en la cuenta para realizar retiros");
			}
		}

		TransactionCompletedEvent processCompletedEvent = TransactionCompletedEvent.builder()
				.transactionId(processTransactionCommand.getTransactionId())
				.accountId(processTransactionCommand.getAccountId()).amount(processTransactionCommand.getAmount())
				.transactionType(processTransactionCommand.getTransactionType()).build();

		AggregateLifecycle.apply(processCompletedEvent);
	}

	@EventSourcingHandler
	public void on(AccountCreatedEvent accountCreatedEvent) {
		this.accountId = accountCreatedEvent.getAccountId();
		this.setUserName(accountCreatedEvent.getUserName());
		this.setPassword(accountCreatedEvent.getPassword());
		this.setFirstName(accountCreatedEvent.getFirstName());
		this.setLastName(accountCreatedEvent.getLastName());
		this.setAccountNumber(accountCreatedEvent.getAccountNumber());
		this.setBalance(accountCreatedEvent.getBalance());
	}

	@EventSourcingHandler
	public void on(TransactionCompletedEvent transactionCompletedEvent) {
		if (transactionCompletedEvent.getTransactionType().equals("WITHDRAW"))
			this.balance -= transactionCompletedEvent.getAmount();
		else
			this.balance += transactionCompletedEvent.getAmount();
	}

}
