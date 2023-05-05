package com.example.rest2.api.account;

import com.example.rest2.api.account.account_web.AccountDto;
import com.example.rest2.api.account.account_web.CrateNewAccountDto;
import com.example.rest2.api.accountType.AccountType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private final AccountMapper accountMapper;
    private final AccountMapStruct accountMapStruct;

    @Override
    public List<AccountDto> findAllAccount() {
        List<Account> accounts = accountMapper.selectAccount();
        return accountMapStruct.accountDto(accounts);
    }

    @Override
    public AccountDto createNewAccount(CrateNewAccountDto crateNewAccountDto) {
        Account account = accountMapStruct.createAccountDtoToAccount(crateNewAccountDto);
        accountMapper.insert(account);
        log.info("Account ={}",account.getId());
       return this.findAccountById(account.getId());
    }

    @Override
    public AccountDto findAccountById(Integer id) {
        Account account = accountMapper.selectById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Account with %d is not found",id)));
        return accountMapStruct.accountDto(account);

    }

    @Override
    public Integer deleteAccountById(Integer id) {
        boolean isExitedAccount = accountMapper.existAccountById(id);
        if(isExitedAccount){
            accountMapper.deleteAccountById(id);
            return id;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Account with %d is not found",id));
    }
}
