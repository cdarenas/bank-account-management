package com.xepelinbank.accountmanagement.AccountService;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import com.xepelinbank.accountmanagement.AccountService.command.interceptors.CreateAccountCommandInterceptor;
import com.xepelinbank.accountmanagement.core.config.XStreamConfig;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ XStreamConfig.class })
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateAccountCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(context.getBean(CreateAccountCommandInterceptor.class));
	}

}
