package com.example.todollist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TodoRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private com.example.todollist.entity.TodoStatus status;
    @jakarta.validation.constraints.FutureOrPresent(message = "Due date cannot be in the past")
    private java.time.LocalDateTime dueDate;
}
