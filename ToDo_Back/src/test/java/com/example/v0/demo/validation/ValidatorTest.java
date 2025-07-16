package com.example.v0.demo.validation;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.exception.ValidationException;
import com.example.v0.demo.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class ValidatorTest {

    private final Validator validator = new Validator();

    @Test
    @DisplayName("Accepts a perfectly valid DTO")
    void acceptsValidDto() {
        ToDoDTO valid = ToDoDTO.builder()
                .text("Refactor mapper")
                .priority(1)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        assertThatCode(() -> validator.validate(valid))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Rejects blank text")
    void rejectsBlankText() {
        ToDoDTO bad = ToDoDTO.builder().text("   ").build();

        assertThatThrownBy(() -> validator.validate(bad))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("text");
    }

    @Test
    @DisplayName("Rejects impossible priority")
    void rejectsPriorityOutOfRange() {
        ToDoDTO bad = ToDoDTO.builder().text("oops").priority(5).build();

        assertThatThrownBy(() -> validator.validate(bad))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("priority");
    }

    @Test
    @DisplayName("Rejects dueDate before now")
    void rejectsPastDueDate() {
        ToDoDTO bad = ToDoDTO.builder()
                .text("time traveler")
                .dueDate(LocalDateTime.now().minusDays(1))
                .priority(1)
                .build();

        assertThatThrownBy(() -> validator.validate(bad))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("dueDate");
    }
}

