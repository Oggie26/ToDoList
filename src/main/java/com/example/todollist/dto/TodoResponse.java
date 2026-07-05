package com.example.todollist.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private com.example.todollist.entity.TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    @com.fasterxml.jackson.annotation.JsonProperty("isOverdue")
    public boolean isOverdue() {
        return status != com.example.todollist.entity.TodoStatus.COMPLETED 
                && dueDate != null 
                && dueDate.isBefore(LocalDateTime.now());
    }
}
