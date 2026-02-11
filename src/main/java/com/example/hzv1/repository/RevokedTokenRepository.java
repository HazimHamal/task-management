package com.example.hzv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hzv1.model.RevokedToken;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long> {
    boolean existsByToken(String token);
}