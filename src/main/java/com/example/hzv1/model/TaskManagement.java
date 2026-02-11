package com.example.hzv1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskManagement {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ID")
    private Integer id;

	@JsonProperty("Title")
    private String title;
	
	@JsonProperty("Description")
    private String description;

	@JsonProperty("Status")
    private String status;

	@JsonProperty("Assigned To")
    private String assignedTo;

}