package com.example.hzv1.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hzv1.model.TaskManagement;
import com.example.hzv1.model.TaskRequest;
import com.example.hzv1.model.TaskUpdateRequest;
import com.example.hzv1.repository.TaskManagementRepository;
import com.example.hzv1.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskManagementRepository taskRepo;
    private final TaskService taskService;

    public TaskController(TaskManagementRepository taskRepo, TaskService taskService) {
        this.taskRepo = taskRepo;
        this.taskService = taskService;
    }

    // Fetch all tasks
    @GetMapping
    public List<TaskManagement> getAllTasks() {
        return taskRepo.findAll();
    }

    // Fetch task by ID 
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        return taskRepo.findById(id)
                .<ResponseEntity<?>>map(task -> ResponseEntity.ok(task))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                               .body(Map.of("error", "Task not found")));
    }
    
    @GetMapping("/view")
    public ResponseEntity<?> viewTasks(@RequestParam List<Integer> ids) {
        List<TaskManagement> tasks = taskRepo.findAllById(ids);
        return ResponseEntity.ok(tasks);
    }
    
    // Create a task
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest request) {
        TaskManagement task = new TaskManagement();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setAssignedTo(request.getAssignedTo());

        TaskManagement savedTask = taskService.createTask(task);
        return ResponseEntity.ok(savedTask);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody TaskUpdateRequest request) {

        Optional<TaskManagement> existingTask = taskRepo.findById(id);
        if (existingTask.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", "Task not found"));
        }

        TaskManagement task = existingTask.get();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setAssignedTo(request.getAssignedTo());

        TaskManagement updatedTask = taskRepo.save(task);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        Optional<TaskManagement> existingTask = taskRepo.findById(id);

        if (existingTask.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("error", "Task not found"));
        }

        taskRepo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
    }

}