package com.example.rest2.api.account;

import com.example.rest2.api.account.account_web.AccountDto;
import com.example.rest2.api.account.account_web.CrateNewAccountDto;
import com.example.rest2.api.accountType.webAccountType.AccountTypeDto;

import java.util.List;

public interface AccountService {
    List<AccountDto> findAllAccount();

    AccountDto createNewAccount(CrateNewAccountDto crateNewAccountDto );

    AccountDto findAccountById(Integer id);
    Integer deleteAccountById(Integer id);
}
