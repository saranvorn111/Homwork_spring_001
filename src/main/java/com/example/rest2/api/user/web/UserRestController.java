package com.example.rest2.api.user.web;

import com.example.rest2.api.user.User;
import com.example.rest2.api.user.UserService;
import com.example.rest2.base.BaseRest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private final UserService userService;

    @PutMapping("/{id}")
    public BaseRest<?> updateUserById(@PathVariable("id") Integer id,@RequestBody UpdateUserDto updateUserDto){
        UserDto userDto=userService.updateUserById(id,updateUserDto);
            return BaseRest.builder()
                    .status(true)
                    .code(HttpStatus.OK.value())
                    .message("User have updated successfully.")
                    .timestamp(LocalDateTime.now())
                    .date(userDto)
                    .build();
    }


    @PutMapping("/{id}/is-deleted")
    public BaseRest<?> updateIsDeletedStatusById(@PathVariable Integer id, @RequestBody IsDeletedDto dto ){
        Integer deletedId = userService.updateIsDeletedStatusById(id,dto.status());
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted successfully.")
                .timestamp(LocalDateTime.now())
                .date(deletedId)
                .build();
    }

    @DeleteMapping("/{id}")
    public BaseRest<?> deleteUserById(@PathVariable Integer id){
        Integer deletedId = userService.deleteUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been deleted successfully.")
                .timestamp(LocalDateTime.now())
                .date(deletedId)
                .build();
    }
    //search by id
    @GetMapping("/{id}")
    public BaseRest<?> findUserById(@PathVariable Integer id){
        UserDto userDto = userService.findUserById(id);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been created successfully.")
                .timestamp(LocalDateTime.now())
                .date(userDto)
                .build();
    }

    //find pages
    @GetMapping
    public BaseRest<?> findAllUsers(@RequestParam(name="page",required = false,defaultValue = "1") int page,
                                    @RequestParam(name="limit",required = false,defaultValue = "20") int limit){
        PageInfo<UserDto> userDtoPageInfo = userService.findAllUser(page,limit);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been created successfully.")
                .timestamp(LocalDateTime.now())
                .date(userDtoPageInfo)
                .build();

    }
    //Insert data
    @PostMapping
    public BaseRest<?> createNewUser(@RequestBody @Valid CreateUserDto createUserDto){
        UserDto userDto = userService.createNewUser(createUserDto);
        return BaseRest.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("User have been created successfully.")
                .timestamp(LocalDateTime.now())
                .date(userDto)
                .build();

    }

}
