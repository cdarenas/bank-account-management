package com.xepelinbank.accountmanagement.AccountService.query;

import lombok.Data;

@Data
public class FindAccountByUserNameAndPasswordQuery {

	private String userName;
	private String password;

}
