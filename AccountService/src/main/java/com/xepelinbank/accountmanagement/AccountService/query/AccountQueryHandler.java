package com.xepelinbank.accountmanagement.AccountService.query;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.xepelinbank.accountmanagement.AccountService.core.data.AccountEntity;
import com.xepelinbank.accountmanagement.AccountService.core.data.AccountRepository;
import com.xepelinbank.accountmanagement.AccountService.query.rest.AccountRestModel;
import com.xepelinbank.accountmanagement.AccountService.query.rest.BalanceRestModel;

@Component
public class AccountQueryHandler {

	private final AccountRepository accountRepository;

	public AccountQueryHandler(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@QueryHandler
	public AccountRestModel findAccount(FindAccountQuery query) {

		AccountRestModel accountRest = new AccountRestModel();

		AccountEntity account = accountRepository.findByAccountId(query.getAccountId());

		BeanUtils.copyProperties(account, accountRest);

		return accountRest;
	}

	@QueryHandler
	public BalanceRestModel findAccount(FindBalanceQuery query) {

		BalanceRestModel balanceRest = new BalanceRestModel();

		AccountEntity account = accountRepository.findByAccountId(query.getAccountId());

		BeanUtils.copyProperties(account, balanceRest);

		return balanceRest;
	}

	@QueryHandler
	public AccountRestModel findAccountByUsernameAndPassword(FindAccountByUserNameAndPasswordQuery query) {

		AccountRestModel accountRest = new AccountRestModel();

		List<AccountEntity> accounts = accountRepository.findByUserNameAndPassword(query.getUserName(),
				query.getPassword());

		BeanUtils.copyProperties(accounts.get(0), accountRest);

		return accountRest;
	}

}
