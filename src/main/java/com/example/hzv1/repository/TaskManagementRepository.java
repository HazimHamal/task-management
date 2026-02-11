package com.example.hzv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hzv1.model.TaskManagement;

public interface TaskManagementRepository extends JpaRepository<TaskManagement, Integer> {
}