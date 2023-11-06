package com.xepelinbank.accountmanagement.AccountService.core.data;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class AccountEntity implements Serializable {

	private static final long serialVersionUID = 3051390119197884318L;

	@Id
	@Column(unique = true)
	private String accountId;
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String accountNumber;
	private Float balance;

}
