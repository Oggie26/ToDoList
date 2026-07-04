package com.example.todollist.service.impl;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import com.example.todollist.entity.Todo;
import com.example.todollist.exception.AppException;
import com.example.todollist.exception.ErrorCode;
import com.example.todollist.mapper.TodoMapper;
import com.example.todollist.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;
    private TodoRequest request;
    private TodoResponse response;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Title");
        todo.setCompleted(false);
        todo.setDeleted(false);

        request = new TodoRequest();
        request.setTitle("Test Title");

        response = new TodoResponse();
        response.setId(1L);
        response.setTitle("Test Title");
        response.setCompleted(false);
    }

    @Test
    void getAllTodos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Todo> todoPage = new PageImpl<>(Collections.singletonList(todo));

        when(todoRepository.findTodosByFilter(any(), any(), eq(pageable))).thenReturn(todoPage);
        when(todoMapper.toResponse(any(Todo.class))).thenReturn(response);

        Page<TodoResponse> result = todoService.getAllTodos(null, null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(todoRepository, times(1)).findTodosByFilter(null, null, pageable);
    }

    @Test
    void getTodoById_Success() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoMapper.toResponse(any(Todo.class))).thenReturn(response);

        TodoResponse result = todoService.getTodoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Title", result.getTitle());
    }

    @Test
    void getTodoById_NotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        AppException exception = assertThrows(AppException.class, () -> todoService.getTodoById(1L));
        assertEquals(ErrorCode.TODO_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void createTodo() {
        when(todoMapper.toEntity(any(TodoRequest.class))).thenReturn(todo);
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        when(todoMapper.toResponse(any(Todo.class))).thenReturn(response);

        TodoResponse result = todoService.createTodo(request);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void updateTodo_Success() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        when(todoMapper.toResponse(any(Todo.class))).thenReturn(response);

        TodoResponse result = todoService.updateTodo(1L, request);

        assertNotNull(result);
        verify(todoMapper, times(1)).updateEntityFromRequest(request, todo);
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void changeStatus_Success() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        when(todoMapper.toResponse(any(Todo.class))).thenReturn(response);

        TodoResponse result = todoService.changeStatus(1L, true);

        assertNotNull(result);
        assertTrue(todo.isCompleted());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void deleteTodo_Success() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.deleteTodo(1L);

        assertTrue(todo.isDeleted());
        verify(todoRepository, times(1)).save(todo);
    }
}
