package com.example.demo.User.UserService;

import com.example.demo.User.UserModel.UserModel;
import com.example.demo.User.UserValidation.EmailAlreadyRegisteredException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service

public interface UserService extends UserDetailsService {
    void registerUser(UserModel userModel);
    boolean verifyAccount(String verificationToken);
//    boolean isAccountVerified(String email);

    String generateJwtToken(String email, String password, String user);

//    public void login( LoginRequest loginRequest);
 void createAdminUser(String email, String password) throws EmailAlreadyRegisteredException;




    boolean hasAdminRole(String email);
}