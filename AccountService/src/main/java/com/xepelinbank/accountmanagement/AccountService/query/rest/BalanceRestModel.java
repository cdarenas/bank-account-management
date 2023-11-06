package com.xepelinbank.accountmanagement.AccountService.query.rest;

import lombok.Data;

@Data
public class BalanceRestModel {

	private String accountId;
	private String accountNumber;
	private Float balance;
}
