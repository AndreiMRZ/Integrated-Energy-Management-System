package com.example.deviceBackend.config;

import com.example.deviceBackend.utils.JwtRequestFilter;
import com.example.deviceBackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        MvcRequestMatcher addMatcher = new MvcRequestMatcher(null, "/device/addDevice");
        MvcRequestMatcher findDeviceMatcher = new MvcRequestMatcher(null, "/device/findDeviceByName/{name}");
        MvcRequestMatcher deleteMatcher = new MvcRequestMatcher(null, "/device/deleteDevice");
        MvcRequestMatcher updateMatcher = new MvcRequestMatcher(null, "/device/update/{id}");
        MvcRequestMatcher userInsertMatcher = new MvcRequestMatcher(null, "/userDevice/insertUser");
        MvcRequestMatcher userDeleteMatcher = new MvcRequestMatcher(null, "/userDevice/deleteUser/{userId}");
        MvcRequestMatcher userWithoutDevMatcher = new MvcRequestMatcher(null, "/userDevice/withoutDevices");
        MvcRequestMatcher findAllMatcher = new MvcRequestMatcher(null, "/device/findAll");

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(authz -> authz
                        .requestMatchers(addMatcher,findDeviceMatcher,deleteMatcher,updateMatcher,userDeleteMatcher,userInsertMatcher,userWithoutDevMatcher,findAllMatcher).permitAll()
                        .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class));

        return http.build();
    }

}
