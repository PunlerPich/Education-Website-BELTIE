package com.example.demo.User.UserController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DefController {
    @RequestMapping("/")
    public String Welcome(){
        return "Welcome To SpringBoot";
    }
}
