package com.example.rest2.api.user;

import com.example.rest2.api.user.web.CreateUserDto;
import com.example.rest2.api.user.web.UpdateUserDto;
import com.example.rest2.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;

public interface UserService {
    UserDto createNewUser(CreateUserDto createUserDto);
    UserDto findUserById(Integer id);

    //add one more
    PageInfo<UserDto> findAllUser(int page,int limit);

    Integer deleteUserById(Integer id);
    Integer updateIsDeletedStatusById(Integer id,boolean status);


    UserDto updateUserById(Integer id, UpdateUserDto updateUserDto);
}
