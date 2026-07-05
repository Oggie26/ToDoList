package com.example.todollist.service;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {
    Page<TodoResponse> getAllTodos(com.example.todollist.entity.TodoStatus status, String title, int page, int size, String sortBy, String direction);
    TodoResponse getTodoById(Long id);
    TodoResponse createTodo(TodoRequest request);
    TodoResponse updateTodo(Long id, TodoRequest request);
    TodoResponse changeStatus(Long id, com.example.todollist.entity.TodoStatus status);
    void deleteTodo(Long id);
}
