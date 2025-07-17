package com.example.v0.demo.mapper;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.model.ToDo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDoDTO toDto(ToDo entity);
    ToDo   toEntity(ToDoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget ToDo target, ToDoDTO source);
}


