package com.xepelinbank.accountmanagement.AccountService.query.rest;

import java.time.Duration;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xepelinbank.accountmanagement.AccountService.query.FindAccountByUserNameAndPasswordQuery;
import com.xepelinbank.accountmanagement.AccountService.query.FindAccountQuery;
import com.xepelinbank.accountmanagement.AccountService.query.FindBalanceQuery;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/accounts")
@CrossOrigin({ "http://localhost:3000" })
public class AccountQueryController {

	private final QueryGateway queryGateway;

	public AccountQueryController(QueryGateway queryGateway) {
		this.queryGateway = queryGateway;
	}

	@GetMapping("/{id}")
	public AccountRestModel getAccount(@PathVariable String id) {
		FindAccountQuery findAccountQuery = new FindAccountQuery();
		findAccountQuery.setAccountId(id);
		AccountRestModel account = queryGateway
				.query(findAccountQuery, ResponseTypes.instanceOf(AccountRestModel.class)).join();

		return account;
	}

	@PostMapping("/login")
	public AccountRestModel login(@Valid @RequestBody LoginRestModel loginRestModel) {
		FindAccountByUserNameAndPasswordQuery findAccountQuery = new FindAccountByUserNameAndPasswordQuery();
		findAccountQuery.setUserName(loginRestModel.getUserName());
		findAccountQuery.setPassword(loginRestModel.getPassword());
		AccountRestModel account = queryGateway
				.query(findAccountQuery, ResponseTypes.instanceOf(AccountRestModel.class)).join();

		return account;
	}

	@GetMapping("/{id}/balance")
	public BalanceRestModel getBalance(@PathVariable String id) {

		return getCurrentBalanceByAccountId(id);
	}

	@GetMapping("{id}/stream-sse")
	public Flux<ServerSentEvent<String>> streamBalance(@PathVariable String id) {
		return Flux.interval(Duration.ofSeconds(3))
				.map(sequence -> ServerSentEvent.<String>builder().id(String.valueOf(sequence)).event("periodic-event")
						.data("Account Balance: USD " + getCurrentBalanceByAccountId(id).getBalance().toString())
						.build());
	}

	private BalanceRestModel getCurrentBalanceByAccountId(String id) {
		FindBalanceQuery findAccountQuery = new FindBalanceQuery();
		findAccountQuery.setAccountId(id);
		BalanceRestModel balance = queryGateway
				.query(findAccountQuery, ResponseTypes.instanceOf(BalanceRestModel.class)).join();

		return balance;
	}

}
