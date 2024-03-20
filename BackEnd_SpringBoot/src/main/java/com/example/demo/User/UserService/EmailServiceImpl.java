package com.example.demo.User.UserService;

import com.example.demo.User.UserModel.UserModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void sendVerificationEmail(UserModel userModel, String verificationToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userModel.getEmail());
        message.setSubject("Account Verification");
        message.setText("Please click the following link to verify your account: "
                + "http://localhost:8888/verify?token=" + verificationToken);
        javaMailSender.send(message);
    }
}