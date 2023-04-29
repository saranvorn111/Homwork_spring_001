package com.example.rest2.api.accountType;

import com.example.rest2.api.accountType.web_account.AccountTypeDto;
import com.example.rest2.api.accountType.web_account.CreateAccountTypeDto;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeDto> findAll();

    AccountTypeDto findAccountById(Integer id);

    AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto);


}
