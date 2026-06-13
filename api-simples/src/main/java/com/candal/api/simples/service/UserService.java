package com.candal.api.simples.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.candal.api.simples.model.User;
import com.candal.api.simples.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user){
        if (repository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email já cadastrado");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public boolean validateLogin(String email, String rawPassword){
        Optional<User> userOpt = repository.findByEmail(email);
        if (userOpt.isEmpty()){
            return false;
        }
        return passwordEncoder.matches(rawPassword, userOpt.get().getPassword());
    }
    
}
