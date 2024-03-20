package com.example.demo.User.UserController;

import com.example.demo.User.UserModel.LoginRequest;
import com.example.demo.User.UserModel.LoginResponse;
import com.example.demo.User.UserModel.UserModel;
import com.example.demo.User.UserRepository.UserRepository;
import com.example.demo.User.UserService.EmailService;
import com.example.demo.User.UserService.JwtUtils;
import com.example.demo.User.UserService.UserService;

import com.example.demo.User.UserService.UserServiceImpl;
import com.example.demo.User.UserValidation.EmailAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;

@RestController
@RequestMapping("/news")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, JwtUtils jwtUtils, EmailService emailService, UserRepository userRepository) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @PostMapping("register")
    public ResponseEntity<String> registerUser(@RequestBody UserModel userModel) {
        try {
            if (userModel.getRoles() == null || userModel.getRoles().isEmpty()) {
                userModel.setRoles(new HashSet<>(Collections.singletonList("user"))); // Set default role to "user"
            }

            if (userModel.getRoles().contains("admin")) {
                userService.createAdminUser(userModel.getEmail(), userModel.getPassword());
            } else {
                userService.registerUser(userModel);
            }

            return ResponseEntity.ok("Please check your email to verify your registration");
        } catch (EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.generateJwtToken(loginRequest.getEmail(), loginRequest.getPassword(), "user");
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginAdmin(@RequestBody LoginRequest loginRequest) {
        String token = userService.generateJwtToken(loginRequest.getEmail(), loginRequest.getPassword(), "admin");
        if (token != null && userService.hasAdminRole(loginRequest.getEmail())) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or insufficient privileges");
        }
    }
}
