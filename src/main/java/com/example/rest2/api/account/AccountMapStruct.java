package com.example.rest2.api.account;

import com.example.rest2.api.account.account_web.AccountDto;
import com.example.rest2.api.account.account_web.CrateNewAccountDto;
import com.example.rest2.api.accountType.AccountType;
import com.example.rest2.api.accountType.webAccountType.AccountTypeDto;
import com.example.rest2.api.accountType.webAccountType.CreateAccountTypeDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface AccountMapStruct {
    List<AccountDto> accountDto(List<Account> accounts);

    AccountDto accountDto(Account account);
    Account createAccountDtoToAccount(CrateNewAccountDto crateNewAccountDto);


}
