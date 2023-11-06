package com.xepelinbank.accountmanagement.AccountService.query;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.xepelinbank.accountmanagement.AccountService.core.data.AccountEntity;
import com.xepelinbank.accountmanagement.AccountService.core.data.AccountRepository;
import com.xepelinbank.accountmanagement.AccountService.core.events.AccountCreatedEvent;
import com.xepelinbank.accountmanagement.core.events.TransactionCompletedEvent;

@Component
@ProcessingGroup("account-group")
public class AccountEventHandler {

	private final AccountRepository accountRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountEventHandler.class);

	public AccountEventHandler(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@EventHandler
	public void on(AccountCreatedEvent event) {

		AccountEntity accountEntity = new AccountEntity();
		BeanUtils.copyProperties(event, accountEntity);

		accountRepository.save(accountEntity);

	}

	@EventHandler
	public void on(TransactionCompletedEvent transactionCompletedEvent) {

		AccountEntity accountEntity = accountRepository.findByAccountId(transactionCompletedEvent.getAccountId());
		float currentBalance = accountEntity.getBalance();

		LOGGER.info("The current balance is: " + currentBalance);
		LOGGER.info("The transaction type is: " + transactionCompletedEvent.getTransactionType());

		if (transactionCompletedEvent.getTransactionType().equals("WITHDRAW"))
			currentBalance -= transactionCompletedEvent.getAmount();
		else
			currentBalance += transactionCompletedEvent.getAmount();

		LOGGER.info("The updated balance is: " + currentBalance);
		accountEntity.setBalance(currentBalance);

		accountRepository.save(accountEntity);
	}

}
