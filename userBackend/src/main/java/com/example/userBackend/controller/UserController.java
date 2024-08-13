package com.example.userBackend.controller;

import com.example.userBackend.dto.UserDto;
import com.example.userBackend.model.User;
import com.example.userBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/insert")
    public ResponseEntity insert (@RequestBody UserDto userDto){
        Long insDto = userService.insert(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(insDto);
    }

    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody UserDto userDto){
        UserDto saveDto = userService.addUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveDto);
    }
    @GetMapping("/findUserById/{id}")
    public ResponseEntity findUserById(@PathVariable long id){
        UserDto userDto1 = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto1);
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/findAllUsers")
    public ResponseEntity<List<UserDto>> findAllUsers (){
        List<UserDto> userDtos = userService.findAllUsers();
        if(userDtos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userDtos, HttpStatus.OK);
        }
    }
    @GetMapping("/findUserByName/{name}")
    public ResponseEntity findUserByName(@PathVariable("name") String name){
        UserDto userDto = userService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
