package com.example.rest2.api.user;

import com.example.rest2.api.Auth.web.RegisterDto;
import com.example.rest2.api.user.web.CreateUserDto;
import com.example.rest2.api.user.web.UpdateUserDto;
import com.example.rest2.api.user.web.UserDto;
import com.github.pagehelper.PageInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapStruct {
    User createUserDtoToUser(CreateUserDto createUserDto);
    User updateUserDtoToUser(UpdateUserDto updateUserDto);

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto user);

    //add new page in pageInfo
    PageInfo<UserDto> userPageInforToUserDtoPageInfo(PageInfo<User> userPageInfo);

    //Auth Mapstruct
    User registerDtoToUser(RegisterDto registerDto);
}
