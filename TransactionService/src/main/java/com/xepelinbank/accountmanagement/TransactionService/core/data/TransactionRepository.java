package com.xepelinbank.accountmanagement.TransactionService.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
	TransactionEntity findByTransactionId(String orderId);

}
