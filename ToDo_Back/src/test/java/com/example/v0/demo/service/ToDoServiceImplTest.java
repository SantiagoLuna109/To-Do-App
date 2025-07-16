package com.example.v0.demo.service;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.mapper.ToDoMapper;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ToDoServiceImplTest {

    private ToDoRepository repo;
    private ToDoServiceImpl service;

    @BeforeEach
    void setUp() {
        repo   = mock(ToDoRepository.class);
        ToDoMapper mapper = Mappers.getMapper(ToDoMapper.class);
        service = new ToDoServiceImpl(repo, mapper);
    }

    @Test
    @DisplayName("Successfully creates a new To-Do and returns DTO with generated id")
    void createsNewTodo() {
        ToDoDTO incoming    = ToDoDTO.builder().text("Write unit tests").priority(2).build();
        ToDo    savedEntity = ToDo.builder().id(1L).text("Write unit tests").priority(2).build();

        when(repo.save(any(ToDo.class))).thenReturn(savedEntity);

        ToDoDTO result = service.create(incoming);

        assertThat(result.getId()).isEqualTo(1L);

        ArgumentCaptor<ToDo> captor = ArgumentCaptor.forClass(ToDo.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getText()).isEqualTo("Write unit tests");
    }

    @Test
    @DisplayName("Throws IllegalArgumentException when marking non-existent To-Do as done")
    void throwsWhenNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.markDone(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}




