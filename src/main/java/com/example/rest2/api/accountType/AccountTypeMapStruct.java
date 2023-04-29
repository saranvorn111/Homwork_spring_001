package com.example.rest2.api.accountType;

import com.example.rest2.api.accountType.web_account.AccountTypeDto;
import com.example.rest2.api.accountType.web_account.CreateAccountTypeDto;
import com.example.rest2.api.user.User;
import com.example.rest2.api.user.web.CreateUserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface AccountTypeMapStruct {
    List<AccountTypeDto> toDto(List<AccountType> model);
    AccountTypeDto toDto(AccountType accountType);

    AccountType createAccountTypeDtoToAccountType(CreateAccountTypeDto createAccountTypeDto);
}
