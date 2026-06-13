package com.candal.api.simples.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.candal.api.simples.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
     boolean existsByEmail(String email);
    
}
