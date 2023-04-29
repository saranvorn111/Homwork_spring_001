package com.example.rest2.api.accountType;

import com.example.rest2.api.accountType.web_account.AccountTypeDto;
import com.example.rest2.api.accountType.web_account.CreateAccountTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeMapper accountTypeMapper;
    private final AccountTypeMapStruct accountTypeMapStruct;


    @Override
    public List<AccountTypeDto> findAll() {
        List<AccountType> accountTypes = accountTypeMapper.select();
        return accountTypeMapStruct.toDto(accountTypes);
//        List<AccountTypeDto> accountTypeDtoList = accountTypes.stream()
//                .map(accountType -> new AccountTypeDto(accountType.getName()))
//                .toList();
//        System.out.println();
//         return accountTypes;
    }
//    @Override
//    public AccountTypeDto findAllAccountById(Integer id) {
//        AccountType accountType = accountTypeMapper.selectById(id).orElseThrow(()->
//                new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        String.format("Account with %d is not found",id)));
//        return accountTypeMapStruct.toDto(accountType);
//    }
//
//    @Override
//    public AccountTypeDto createNewAccountType(CreateAccountTypeDto createAccountTypeDto) {
//        AccountType accountType = accountTypeMapStruct.createAccountTypeDtoToAccountType(createAccountTypeDto);
//        accountTypeMapper.insert(accountType);
//        return this.findAllAccountById(accountType.getId());
//    }


}
