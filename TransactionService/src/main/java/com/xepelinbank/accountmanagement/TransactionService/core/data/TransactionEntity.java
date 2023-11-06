package com.xepelinbank.accountmanagement.TransactionService.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

import com.xepelinbank.accountmanagement.TransactionService.core.model.TransactionStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class TransactionEntity implements Serializable {

	private static final long serialVersionUID = -8064198478434500758L;

	@Id
	@Column(unique = true)
	public String transactionId;
	private String accountId;
	private String userName;
	private String transactionType;
	private Float amount;

	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;
}
