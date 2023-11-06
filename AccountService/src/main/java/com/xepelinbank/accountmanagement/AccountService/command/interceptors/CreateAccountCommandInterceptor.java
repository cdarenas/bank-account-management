package com.xepelinbank.accountmanagement.AccountService.command.interceptors;

import java.util.List;
import java.util.function.BiFunction;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xepelinbank.accountmanagement.AccountService.command.CreateAccountCommand;

@Component
public class CreateAccountCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccountCommandInterceptor.class);

	@Override
	public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
			List<? extends CommandMessage<?>> messages) {

		return (index, command) -> {

			LOGGER.info("Intercepted command: " + command.getPayloadType());

			if (CreateAccountCommand.class.equals(command.getPayloadType())) {

				CreateAccountCommand createAccountCommand = (CreateAccountCommand) command.getPayload();

				if (createAccountCommand.getBalance().compareTo(0f) <= 0) {
					throw new IllegalArgumentException("El saldo inicial de la cuenta debe ser mayor a cero");
				}

				if (createAccountCommand.getAccountNumber() == null
						|| createAccountCommand.getAccountNumber().isBlank()) {
					throw new IllegalArgumentException("El numero de cuenta no puede ser nulo");
				}

			}

			return command;
		};
	}

}
