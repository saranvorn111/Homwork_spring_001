package com.example.rest2.api.accountType;

import com.example.rest2.api.accountType.webAccountType.AccountTypeDto;
import com.example.rest2.api.accountType.webAccountType.CreateAccountTypeDto;
import com.example.rest2.api.accountType.webAccountType.UpdateAccountDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface AccountTypeMapStruct {
    List<AccountTypeDto> toDto(List<AccountType> model);
    AccountTypeDto toDto(AccountType accountType);
    AccountType updateAccountDtoToAccountType (UpdateAccountDto updateAccountDto);

    AccountType createAccountTypeDtoToAccountType(CreateAccountTypeDto createAccountTypeDto);


}
