package com.example.rest2.api.account.account_web;

import lombok.Builder;


public record CrateNewAccountDto(String accountNo,
                                 String accountName,
                                 String profile,
                                 Integer pin,
                                 String password,
                                 String phoneNumber,
                                 Integer transferLimit,
                                 Integer accountType) {
}
