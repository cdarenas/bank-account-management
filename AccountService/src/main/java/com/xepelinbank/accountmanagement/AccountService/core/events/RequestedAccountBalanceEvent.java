package com.xepelinbank.accountmanagement.AccountService.core.events;

import lombok.Data;

@Data
public class RequestedAccountBalanceEvent {

	private String accountId;

}
