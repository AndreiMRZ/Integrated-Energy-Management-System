package com.example.userBackend.impl;

import com.example.userBackend.dto.UserDto;
import com.example.userBackend.model.User;
import com.example.userBackend.repo.UserRepo;
import com.example.userBackend.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
   private UserRepo userRepo;
   private ModelMapper mapper;
   private RestTemplate restTemplate;

    @Override
    public UserDto addUser(UserDto userDto) {
        List<User> users = userRepo.findAll();
        if (users.size() == 0){
            userDto.setAdmin(true);
        }
        User user = mapper.map(userDto, User.class);
        user = userRepo.save(user);
        return mapper.map(user,UserDto.class);
    }

    @Override
    public UserDto findByName(String name) {
       User user  = userRepo.findUserByUsername(name);
       if(user == null){
           return null;
       }
       UserDto userDto = mapper.map(user,UserDto.class);
       return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepo.findAll();
        //return (List<UserDto>) mapper.map(users, UserDto.class);
        return users.stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(long id) {
       Optional<User> user = userRepo.findById(id);
       if(user == null){
           return null;
       }
       UserDto userDto = mapper.map(user, UserDto.class);
       return userDto;
    }

    @Override
    public void deleteUser(long id) {

        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
           // String copy = "{userId}";
            String apiUrl = "http://host.docker.internal:8081/userDevice/deleteUser/" + user.getId();
            try{
                ResponseEntity<Response> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.DELETE, null, Response.class);
                if(responseEntity.getStatusCode() == HttpStatus.OK){
                    Response response = responseEntity.getBody();
                } else {
                    System.out.println("Response Status: " + responseEntity.getStatusCode());
                    System.out.println("Response Body: " + responseEntity.getBody());
                }}catch (RestClientException e){
                e.printStackTrace();
            }
            userRepo.delete(user);
        }
    }

    @Override
    public Long insert(UserDto userDto) {

        // Check if it's the first user
        long userCount = userRepo.count();
        if (userCount == 0){
            userDto.setAdmin(true);
        }

        // Map DTO to entity and save
        User user = mapper.map(userDto, User.class);
        user.setId(null); // Ensure the ID is null for a new insert
        user = userRepo.save(user);

        // Post saved user's ID to another service
        String apiUrl = "http://host.docker.internal:8081/userDevice/insertUser";
        try {
            ResponseEntity<Response> responseEntity = restTemplate.postForEntity(apiUrl, user.getId(), Response.class);

            if(responseEntity.getStatusCode() != HttpStatus.OK){
                System.out.println("Response Status: " + responseEntity.getStatusCode());
                System.out.println("Response Body: " + responseEntity.getBody());
            }
        } catch (RestClientException e){
            e.printStackTrace();
        }

        return user.getId();

    }

//    @Override
//    public User findByUserName(String name) {
//        Optional<User> users = userRepo.findByUsername(name);
//        if(users.isPresent()){
//            User user = users.get();
//            return user;
//        }
//        return null;
//    }
}
