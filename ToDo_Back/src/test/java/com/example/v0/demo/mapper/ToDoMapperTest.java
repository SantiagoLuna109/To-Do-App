package com.example.v0.demo.mapper;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ToDoMapperTest {

    private final ToDoMapper mapper = Mappers.getMapper(ToDoMapper.class);

    @Test
    @DisplayName("Maps DTO → Entity and back without losing data")
    void roundTripMapping() {
        ToDoDTO dto = ToDoDTO.builder()
                .id(42L)
                .text("Learn Testcontainers")
                .priority(2)
                .dueDate(LocalDateTime.now().plusDays(3))
                .doneFlag(false)
                .build();

        ToDo entity   = mapper.toEntity(dto);
        ToDoDTO back  = mapper.toDto(entity);

        assertThat(back)
                .usingRecursiveComparison()
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Gracefully handles null DTO fields")
    void handlesNullFields() {
        ToDoDTO dto  = ToDoDTO.builder().text("Just text").build();
        ToDo   entity = mapper.toEntity(dto);

        assertThat(entity.getPriority()).isEqualTo(3);
        assertThat(entity.getDueDate()).isNull();
    }
}



