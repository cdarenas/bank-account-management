package com.xepelinbank.accountmanagement.AccountService.query;

import lombok.Data;

@Data
public class FindAccountQuery {

	private String accountId;
	private String userName;
	private String password;
}
