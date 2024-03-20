package com.example.demo.User.UserService;

import com.example.demo.User.UserModel.LoginRequest;
import com.example.demo.User.UserModel.UserModel;
import com.example.demo.User.UserRepository.UserRepository;
import com.example.demo.User.UserValidation.EmailAlreadyRegisteredException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserRepository userRepository, EmailService emailService,
                           AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder,
                            JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;

    }

    @Override
    public void registerUser(UserModel userModel) throws EmailAlreadyRegisteredException {
        // Check if the email is already registered
        if (isEmailAlreadyRegistered(userModel.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        // Encode the password using BCrypt
        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);

        // Generate the verification token
        String verificationToken = generateVerificationToken();
        userModel.setVerificationToken(verificationToken);
        userModel.setVerified(false); // Set the initial verification status to false

        // Send the verification email
        emailService.sendVerificationEmail(userModel, verificationToken);

        // Save the user in the database
        userRepository.save(userModel);
    }
    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }
    private boolean isEmailAlreadyRegistered(String email) {
        // Implement the logic to check if the email is already registered in your UserRepository
        // You can modify this according to your application's data access implementation
        UserModel existingUser = userRepository.findByEmail(email);
        return existingUser != null;
    }

    @Override
    public boolean verifyAccount(String verificationToken) {
        UserModel user = userRepository.findByVerificationToken(verificationToken);

        if (user != null) {
            user.setVerified(true);
            user.setVerificationToken("Success");
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);
        if (user == null || !user.isVerified()) {
            throw new UsernameNotFoundException("User not found");
        }

        // Retrieve the user's roles from the UserModel entity
        Set<String> roles = user.getRoles();

        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    @Override
    public String generateJwtToken(String email, String password, String roles) {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            return null; // User not found
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null; // Invalid password
        }

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token with appropriate claims
            String token = jwtUtils.generateToken(email, roles); // Pass the roles parameter to generateToken

            return token; // Return the generated token
        } catch (AuthenticationException e) {
            return null; // Login failed
        }
    }
    @Override
    public boolean hasAdminRole(String email) {
        UserModel user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getRoles().contains("admin");
        }
        return false;
    }
    @Override
    public void createAdminUser(String email, String password) throws EmailAlreadyRegisteredException {
        // Check if the email is already registered
        if (isEmailAlreadyRegistered(email)) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        // Encode the password using BCrypt
        String encodedPassword = passwordEncoder.encode(password);

        // Generate the verification token
        String verificationToken = generateVerificationToken();

        // Create the admin user
        UserModel adminUser = new UserModel();
        adminUser.setEmail(email);
        adminUser.setPassword(encodedPassword);
        adminUser.setRoles(new HashSet<>(Collections.singletonList("admin")));
        adminUser.setVerified(false); // Set the initial verification status to false
        adminUser.setVerificationToken(verificationToken);

        // Send the verification email
        emailService.sendVerificationEmail(adminUser, verificationToken);

        // Save the admin user in the database
        userRepository.save(adminUser);

    }

}
