package com.example.hzv1.service;

import org.springframework.stereotype.Service;

import com.example.hzv1.model.TaskManagement;
import com.example.hzv1.repository.TaskManagementRepository;


@Service
public class TaskService {

    private final TaskManagementRepository taskRepository;

    public TaskService(TaskManagementRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskManagement createTask(TaskManagement task) {
        return taskRepository.save(task);
    }
}