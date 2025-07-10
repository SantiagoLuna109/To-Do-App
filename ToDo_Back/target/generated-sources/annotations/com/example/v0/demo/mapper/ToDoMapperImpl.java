package com.example.v0.demo.mapper;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.model.ToDo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-10T01:40:37-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ToDoMapperImpl implements ToDoMapper {

    @Override
    public ToDoDTO toDto(ToDo entity) {
        if ( entity == null ) {
            return null;
        }

        ToDoDTO toDoDTO = new ToDoDTO();

        return toDoDTO;
    }

    @Override
    public ToDo toEntity(ToDoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ToDo toDo = new ToDo();

        return toDo;
    }

    @Override
    public List<ToDoDTO> toDtoList(List<ToDo> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ToDoDTO> list = new ArrayList<ToDoDTO>( entities.size() );
        for ( ToDo toDo : entities ) {
            list.add( toDto( toDo ) );
        }

        return list;
    }

    @Override
    public List<ToDo> toEntityList(List<ToDoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<ToDo> list = new ArrayList<ToDo>( dtos.size() );
        for ( ToDoDTO toDoDTO : dtos ) {
            list.add( toEntity( toDoDTO ) );
        }

        return list;
    }

    @Override
    public void updateEntity(ToDo target, ToDoDTO source) {
        if ( source == null ) {
            return;
        }
    }
}
