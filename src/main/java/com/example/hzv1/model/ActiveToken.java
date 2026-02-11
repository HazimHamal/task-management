package com.example.hzv1.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "active_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveToken {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    
    public ActiveToken(String username, String token, LocalDateTime issuedAt) {
        this.username = username;
        this.token = token;
        this.issuedAt = LocalDateTime.now();
    }


}