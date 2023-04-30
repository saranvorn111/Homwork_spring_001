package com.example.rest2.api.user.web;

import lombok.Builder;


public record UserDto(String name,
                      String gender,
                      String studentCardId,
                      Boolean isStudent) {
}
