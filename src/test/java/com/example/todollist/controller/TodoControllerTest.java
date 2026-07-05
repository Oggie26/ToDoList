package com.example.todollist.controller;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import com.example.todollist.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.todollist.enums.TodoStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTodos() throws Exception {
        TodoResponse todo = new TodoResponse();
        todo.setId(1L);
        todo.setTitle("Test task");
        todo.setStatus(TodoStatus.TODO);

        when(todoService.getAllTodos(any(), any(), org.mockito.ArgumentMatchers.anyInt(), org.mockito.ArgumentMatchers.anyInt(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(todo), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].title").value("Test task"));
    }

    @Test
    public void testCreateTodo_Success() throws Exception {
        TodoRequest newTodo = new TodoRequest();
        newTodo.setTitle("New Task");

        TodoResponse savedTodo = new TodoResponse();
        savedTodo.setId(1L);
        savedTodo.setTitle("New Task");
        
        when(todoService.createTodo(any())).thenReturn(savedTodo);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("New Task"));
    }
    
    @Test
    public void testCreateTodo_ValidationError() throws Exception {
        TodoRequest newTodo = new TodoRequest();
        newTodo.setTitle(""); // empty title

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.title").exists());
    }
}

