package com.example.userBackend.controller;

import com.example.userBackend.dto.AuthenticationResponse;
import com.example.userBackend.dto.UserDto;
import com.example.userBackend.impl.MyUserDetailsService;
import com.example.userBackend.model.User;
import com.example.userBackend.repo.UserRepo;
import com.example.userBackend.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class AuthenticationController {
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private MyUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        Optional<User> user = userRepo.findByUsername(userDto.getUsername());
        if(user.isPresent() && user.get().getPassword().equals(userDto.getPassword())){
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        else{
            return ResponseEntity.status(401).body("Incorect user or pass");
        }

    }
}
