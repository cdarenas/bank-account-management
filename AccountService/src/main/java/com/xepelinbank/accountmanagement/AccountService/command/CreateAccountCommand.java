package com.xepelinbank.accountmanagement.AccountService.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateAccountCommand {

	@TargetAggregateIdentifier
	private final String accountId;
	private final String userName;
	private final String password;
	private final String firstName;
	private final String lastName;
	private final String accountNumber;
	private final Float balance;
}
