package com.example.todollist.service;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import com.example.todollist.enums.TodoStatus;
import org.springframework.data.domain.Page;

public interface TodoService {
    Page<TodoResponse> getAllTodos(TodoStatus status, String title, int page, int size, String sortBy, String direction);
    TodoResponse getTodoById(Long id);
    TodoResponse createTodo(TodoRequest request);
    TodoResponse updateTodo(Long id, TodoRequest request);
    TodoResponse changeStatus(Long id, TodoStatus status);
    void deleteTodo(Long id);
}
