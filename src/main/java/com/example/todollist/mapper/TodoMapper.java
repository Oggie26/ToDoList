package com.example.todollist.mapper;

import com.example.todollist.dto.TodoRequest;
import com.example.todollist.dto.TodoResponse;
import com.example.todollist.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toEntity(TodoRequest request);
    TodoResponse toResponse(Todo todo);
    void updateEntityFromRequest(TodoRequest request, @MappingTarget Todo todo);
}
