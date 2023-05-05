package com.example.rest2.api.account.account_web;

import com.example.rest2.api.account.AccountService;
import com.example.rest2.base.BaseRest;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountRestController {
    private final AccountService accountService;

    @GetMapping
    public BaseRest<?> findAllAccount(){
        var accountDtoList = accountService.findAllAccount();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been found")
                .timestamp(LocalDateTime.now())
                .date(accountDtoList)
                .build();

    }

    @GetMapping("/{id}")
    public BaseRest<?> findAccountById(@PathVariable Integer id){
        AccountDto accountDto = accountService.findAccountById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been found")
                .timestamp(LocalDateTime.now())
                .date(accountDto)
                .build();
    }

    @PostMapping
    public BaseRest<?> createNewAccount(@RequestBody @Valid CrateNewAccountDto crateNewAccountDto){
        AccountDto accountDto = accountService.createNewAccount(crateNewAccountDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been found")
                .timestamp(LocalDateTime.now())
                .date(accountDto)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteAccountById(@PathVariable Integer id){
        Integer deletedAccountById = accountService.deleteAccountById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been found")
                .timestamp(LocalDateTime.now())
                .date(deletedAccountById)
                .build();
    }
}
