package com.example.v0.demo.mapper;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.model.ToDo;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ToDoMapper {

    ToDoDTO toDto(ToDo entity);
    ToDo   toEntity(ToDoDTO dto);

    List<ToDoDTO> toDtoList(List<ToDo> entities);
    List<ToDo>   toEntityList(List<ToDoDTO> dtos);

    default Page<ToDoDTO> toDtoPage(Page<ToDo> page) {
        return new PageImpl<>(toDtoList(page.getContent()), page.getPageable(), page.getTotalElements());
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ToDo target, ToDoDTO source);
}

