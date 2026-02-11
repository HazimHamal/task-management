package com.example.hzv1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

	@NotBlank(message = "Title is required")
	@JsonProperty("Title")
    private String title;
	
	@JsonProperty("Description")
    private String description;

	@NotBlank(message = "Status is required")
	@JsonProperty("Status")
    private String status;

	@JsonProperty("Assigned To")
    private String assignedTo;

}