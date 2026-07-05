package com.example.todollist.controller;

import com.example.todollist.dto.ApiResponse;
import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import com.example.todollist.service.TodoService;
import com.example.todollist.enums.TodoStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<TodoResponse>> getAllTodos(
            @RequestParam(required = false) TodoStatus status,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        return ApiResponse.<Page<TodoResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Fetched todos successfully")
                .data(todoService.getAllTodos(status, title, page, size, sortBy, direction))
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TodoResponse> getTodoById(@PathVariable Long id) {
        return ApiResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Fetched todo successfully")
                .data(todoService.getTodoById(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request) {
        return ApiResponse.<TodoResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Created todo successfully")
                .data(todoService.createTodo(request))
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TodoResponse> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        return ApiResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Updated todo successfully")
                .data(todoService.updateTodo(id, request))
                .build();
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TodoResponse> changeStatus(@PathVariable Long id, @RequestParam TodoStatus status) {
        return ApiResponse.<TodoResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Changed todo status successfully")
                .data(todoService.changeStatus(id, status))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Deleted todo successfully")
                .build();
    }
}

