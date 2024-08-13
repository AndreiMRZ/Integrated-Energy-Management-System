package com.example.userBackend.service;

import com.example.userBackend.dto.UserDto;
import com.example.userBackend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserService {

    UserDto addUser(UserDto user);

    UserDto findByName(String name);
    List<UserDto> findAllUsers();
    UserDto findUserById(long id);
    void deleteUser(long id);

    public Long insert(UserDto userDto);

}
