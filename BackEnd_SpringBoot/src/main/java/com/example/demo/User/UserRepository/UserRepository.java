package com.example.demo.User.UserRepository;

import com.example.demo.User.UserModel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
//    UserModel findByUsername(String username);

    UserModel findByEmail(String email);

    UserModel findByVerificationToken(String verificationToken);
}
