package com.example.todollist.service;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {
    Page<TodoResponse> getAllTodos(Boolean completed, String title, Pageable pageable);
    TodoResponse getTodoById(Long id);
    TodoResponse createTodo(TodoRequest request);
    TodoResponse updateTodo(Long id, TodoRequest request);
    TodoResponse changeStatus(Long id, boolean completed);
    void deleteTodo(Long id);
}
