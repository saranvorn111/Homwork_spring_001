package com.example.rest2.api.accountType.web_account;

import com.example.rest2.api.accountType.AccountTypeService;
import com.example.rest2.base.BaseRest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account-types")
public class AccountTypeRestController {
    private final AccountTypeService accountTypeService;
    @GetMapping
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
//    @PostMapping
//    public BaseRest<?> createNewAccountType(@RequestBody @Valid CreateAccountTypeDto createAccountTypeDto){
//        AccountTypeDto accountTypeDto = accountTypeService.createNewAccountType(createAccountTypeDto);
//        return BaseRest.builder()
//                .status(true)
//                .code(HttpStatus.OK.value())
//                .message("Account have insert successfully")
//                .timestamp(LocalDateTime.now())
//                .date(accountTypeDto)
//                .build();
//    }

}
