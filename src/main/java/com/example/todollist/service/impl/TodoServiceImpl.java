package com.example.todollist.service.impl;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import com.example.todollist.entity.Todo;
import com.example.todollist.exception.AppException;
import com.example.todollist.exception.ErrorCode;
import com.example.todollist.mapper.TodoMapper;
import com.example.todollist.repository.TodoRepository;
import com.example.todollist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public Page<TodoResponse> getAllTodos(com.example.todollist.entity.TodoStatus status, String title, int page, int size, String sortBy, String direction) {
        org.springframework.data.domain.Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? org.springframework.data.domain.Sort.Direction.ASC : org.springframework.data.domain.Sort.Direction.DESC;
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, org.springframework.data.domain.Sort.by(sortDirection, sortBy));
        Page<Todo> todos = todoRepository.findTodosByFilter(status, title, pageable);
        return todos.map(todoMapper::toResponse);
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TODO_NOT_FOUND));
        return todoMapper.toResponse(todo);
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(com.example.todollist.entity.TodoStatus.TODO);
        }
        Todo todo = todoMapper.toEntity(request);
        return todoMapper.toResponse(todoRepository.save(todo));
    }

    @Override
    @Transactional
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        Todo checkTodo = todoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TODO_NOT_FOUND));
        todoMapper.updateEntityFromRequest(request, checkTodo);
        return todoMapper.toResponse(todoRepository.save(checkTodo));
    }

    @Override
    @Transactional
    public TodoResponse changeStatus(Long id, com.example.todollist.entity.TodoStatus status) {
        Todo checkTodo = todoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TODO_NOT_FOUND));
        checkTodo.setStatus(status);
        return todoMapper.toResponse(todoRepository.save(checkTodo));
    }

    @Override
    @Transactional
    public void deleteTodo(Long id) {
        Todo checkTodo = todoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TODO_NOT_FOUND));
        checkTodo.setDeleted(true);
        todoRepository.save(checkTodo);
    }
}
