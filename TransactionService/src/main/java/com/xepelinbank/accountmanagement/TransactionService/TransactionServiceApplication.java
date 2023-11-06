package com.xepelinbank.accountmanagement.TransactionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.xepelinbank.accountmanagement.core.config.XStreamConfig;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ XStreamConfig.class })
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}
