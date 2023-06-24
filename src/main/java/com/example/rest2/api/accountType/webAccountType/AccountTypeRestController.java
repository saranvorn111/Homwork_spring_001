package com.example.rest2.api.accountType.webAccountType;

import com.example.rest2.api.accountType.AccountTypeService;
import com.example.rest2.api.user.web.IsDeletedDto;
import com.example.rest2.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_account:read')")
    public BaseRest<?>findAll(){
        var accountTypeDtoList = accountTypeService.findAll();
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been found")
                .timestamp(LocalDateTime.now())
                .date(accountTypeDtoList)
                .build();

    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_account:read')")
    public BaseRest<?> finAccountById(@PathVariable Integer id){
        AccountTypeDto accountTypeDto = accountTypeService.findAccountById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been create successfully")
                .timestamp(LocalDateTime.now())
                .date(accountTypeDto)
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_account:write')")
    public BaseRest<?> createNewAccountType(@RequestBody @Valid CreateAccountTypeDto createAccountTypeDto){
        AccountTypeDto accountTypeDto = accountTypeService.createNewAccountType(createAccountTypeDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have insert successfully")
                .timestamp(LocalDateTime.now())
                .date(accountTypeDto)
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_account:update')")
    public BaseRest<?> updateAccountById(@PathVariable("id") Integer id,@RequestBody UpdateAccountDto updateAccountDto){
        AccountTypeDto accountTypeDto = accountTypeService.updateAccountById(id,updateAccountDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been updated successfully")
                .timestamp(LocalDateTime.now())
                .date(accountTypeDto)
                .build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_account:delete')")
    public BaseRest<?> deleteAccountById(@PathVariable Integer id){
        Integer deletedId = accountTypeService.deletedAccountById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been deleted successfully")
                .timestamp(LocalDateTime.now())
                .date(deletedId)
                .build();
    }

    @PutMapping("/{id}/name")
    @PreAuthorize("hasAuthority('SCOPE_account:update')")
    public BaseRest<?> updateIsDeletedStatusById(@PathVariable Integer id, @RequestBody IsDeletedDto dto ){
        Integer deletedId = accountTypeService.updatedIsDeletedStatusAccount(id,dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Account have been deleted successfully.")
                .timestamp(LocalDateTime.now())
                .date(deletedId)
                .build();
    }


}
