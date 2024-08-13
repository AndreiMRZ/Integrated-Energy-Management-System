package com.example.deviceBackend.impl;

import com.example.deviceBackend.dto.UserDto;
import com.example.deviceBackend.model.User;
import com.example.deviceBackend.repo.UserRepo;
import com.example.deviceBackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private ModelMapper mapper;

    @Override
    public UserDto insertUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user = userRepo.save(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
    userRepo.deleteById(id);
    }

    @Override
    public List<User> getUsersWithoutDevices() {
        return userRepo.findByDeviceListIsNull();
    }
}
