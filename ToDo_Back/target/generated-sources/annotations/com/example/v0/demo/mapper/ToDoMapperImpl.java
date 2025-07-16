package com.example.v0.demo.mapper;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.model.ToDo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-16T18:38:21-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ToDoMapperImpl implements ToDoMapper {

    @Override
    public ToDoDTO toDto(ToDo entity) {
        if ( entity == null ) {
            return null;
        }

        ToDoDTO.ToDoDTOBuilder toDoDTO = ToDoDTO.builder();

        toDoDTO.id( entity.getId() );
        toDoDTO.text( entity.getText() );
        toDoDTO.priority( entity.getPriority() );
        toDoDTO.dueDate( entity.getDueDate() );
        toDoDTO.doneFlag( entity.isDoneFlag() );
        toDoDTO.doneDate( entity.getDoneDate() );

        return toDoDTO.build();
    }

    @Override
    public ToDo toEntity(ToDoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ToDo.ToDoBuilder toDo = ToDo.builder();

        toDo.id( dto.getId() );
        toDo.dueDate( dto.getDueDate() );
        toDo.doneDate( dto.getDoneDate() );
        if ( dto.getPriority() != null ) {
            toDo.priority( dto.getPriority() );
        }
        if ( dto.getDoneFlag() != null ) {
            toDo.doneFlag( dto.getDoneFlag() );
        }
        toDo.text( dto.getText() );

        return toDo.build();
    }

    @Override
    public void update(ToDo target, ToDoDTO source) {
        if ( source == null ) {
            return;
        }

        if ( source.getId() != null ) {
            target.setId( source.getId() );
        }
        if ( source.getDueDate() != null ) {
            target.setDueDate( source.getDueDate() );
        }
        if ( source.getDoneDate() != null ) {
            target.setDoneDate( source.getDoneDate() );
        }
        if ( source.getPriority() != null ) {
            target.setPriority( source.getPriority() );
        }
        if ( source.getDoneFlag() != null ) {
            target.setDoneFlag( source.getDoneFlag() );
        }
        if ( source.getText() != null ) {
            target.setText( source.getText() );
        }
    }
}
