package com.example.demo.User.UserController;

import com.example.demo.User.UserService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String verificationToken) {
        boolean isVerified = userService.verifyAccount(verificationToken);

        if (isVerified) {
            return ResponseEntity.ok("Account successfully verified!");
        } else {
            return ResponseEntity.badRequest().body("Invalid verification token.");
        }
    }
}