package com.example.deviceBackend.service;

import com.example.deviceBackend.dto.UserDto;
import com.example.deviceBackend.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    UserDto insertUser(UserDto userDto);
    void deleteUser(long id);

    public List<User> getUsersWithoutDevices();
}
