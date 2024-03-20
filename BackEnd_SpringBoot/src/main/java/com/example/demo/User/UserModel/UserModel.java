package com.example.demo.User.UserModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private boolean verified;
    private String verificationToken;

    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_model_roles", joinColumns = @JoinColumn(name = "user_model_id"))
    @Column(name = "roles")
    private Set<String> roles;


}