package com.example.deviceBackend.controller;

import com.example.deviceBackend.dto.UserDto;
import com.example.deviceBackend.model.User;
import com.example.deviceBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userDevice")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/insertUser")
    public ResponseEntity insertUser(@RequestBody UserDto userDto){

        UserDto saveDto = userService.insertUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDto);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/withoutDevices")
    public ResponseEntity<List<User>> getUsersWithoutDevices() {
        List<User> users = userService.getUsersWithoutDevices();

        return ResponseEntity.ok(users);
    }
}
