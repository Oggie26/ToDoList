package com.example.todollist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.todollist.enums.TodoStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private TodoStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    @JsonProperty("isOverdue")
    public boolean isOverdue() {
        return status != TodoStatus.COMPLETED
                && dueDate != null 
                && dueDate.isBefore(LocalDateTime.now());
    }
}
