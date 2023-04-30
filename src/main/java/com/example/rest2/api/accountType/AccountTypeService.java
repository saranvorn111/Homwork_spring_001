package com.example.rest2.api.accountType;

import com.example.rest2.api.accountType.web_account.AccountTypeDto;
import com.example.rest2.api.accountType.web_account.CreateAccountTypeDto;
import com.example.rest2.api.accountType.web_account.UpdateAccountDto;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeDto> findAll();

    AccountTypeDto findAccountById(Integer id);

    AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto);

    AccountTypeDto updateAccountById(Integer id, UpdateAccountDto updateAccountDto);
    Integer deletedAccountById(Integer id);

    Integer updatedIsDeletedStatusAccount(Integer id, boolean status);



}
