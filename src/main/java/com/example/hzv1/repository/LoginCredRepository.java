package com.example.hzv1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hzv1.model.LoginCred;

public interface LoginCredRepository extends JpaRepository<LoginCred, Integer> {
	boolean existsByUsername(String username);
    Optional<LoginCred> findByUsername(String username);
    
    Optional<LoginCred> findByUsernameAndPassword(String username, String password);
}