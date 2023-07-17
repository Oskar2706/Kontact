package com.example.kontact.controller;

import com.example.kontact.dto.UserDto;
import com.example.kontact.entity.User;
import com.example.kontact.security.JWTTokenProvider;
import com.example.kontact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<User> registration(@RequestBody User user) {
        return ResponseEntity.ok((User) userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto dto) {
        User user = userService.findByUsername(dto.getUsername());
        String token = jwtTokenProvider.generateToken(dto.getUsername(), user.getRoles());
        if (bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().build();
    }
}