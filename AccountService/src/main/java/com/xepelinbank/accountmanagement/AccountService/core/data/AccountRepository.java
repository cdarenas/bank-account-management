package com.xepelinbank.accountmanagement.AccountService.core.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

	AccountEntity findByAccountId(String accountId);

	AccountEntity findByUserName(String username);

	List<AccountEntity> findByUserNameAndPassword(String username, String password);

}
