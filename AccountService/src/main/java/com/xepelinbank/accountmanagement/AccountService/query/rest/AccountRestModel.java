package com.xepelinbank.accountmanagement.AccountService.query.rest;

import lombok.Data;

@Data
public class AccountRestModel {

	private String accountId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String accountNumber;
	private Float balance;
}
