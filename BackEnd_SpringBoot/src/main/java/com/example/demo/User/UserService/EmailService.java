package com.example.demo.User.UserService;

import com.example.demo.User.UserModel.UserModel;

public interface EmailService {
    void sendVerificationEmail(UserModel userModel, String verificationToken);
}
