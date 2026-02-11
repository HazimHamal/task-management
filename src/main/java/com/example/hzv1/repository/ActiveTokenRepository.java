package com.example.hzv1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hzv1.model.ActiveToken;

public interface ActiveTokenRepository extends JpaRepository<ActiveToken, Integer> {
	boolean existsByToken(String token);
    void deleteByToken(String token);
    Optional<ActiveToken> findByUsername(String username);
    void deleteByUsername(String username);
}