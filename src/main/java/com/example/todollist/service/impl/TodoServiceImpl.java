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
    public Page<TodoResponse> getAllTodos(Boolean completed, String title, Pageable pageable) {
        Page<Todo> todos = todoRepository.findTodosByFilter(completed, title, pageable);
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
    public TodoResponse changeStatus(Long id, boolean completed) {
        Todo checkTodo = todoRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TODO_NOT_FOUND));
        checkTodo.setCompleted(completed);
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
