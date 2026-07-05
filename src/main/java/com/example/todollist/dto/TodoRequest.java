package com.example.todollist.dto;

import com.example.todollist.enums.TodoStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private TodoStatus status;
    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDateTime dueDate;
}
