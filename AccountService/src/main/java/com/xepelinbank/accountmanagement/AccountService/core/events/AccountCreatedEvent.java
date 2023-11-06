package com.xepelinbank.accountmanagement.AccountService.core.events;

import lombok.Data;

@Data
public class AccountCreatedEvent {

	private String accountId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String accountNumber;
	private Float balance;
}