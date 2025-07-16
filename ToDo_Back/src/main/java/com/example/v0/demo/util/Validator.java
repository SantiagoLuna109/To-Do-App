package com.example.v0.demo.util;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

public class Validator {

    public void validate(ToDoDTO dto) {
        isNotEmpty(dto.getText());
        isNotEmptyPriority(dto.getPriority());

        if (dto.getDueDate() != null && !dto.getDueDate().isAfter(LocalDateTime.now())) {
            throw new ValidationException("dueDate must be in the future");
        }
    }


    public static void isNotEmpty(String text) {
        if (StringUtils.isBlank(text)) {
            throw new ValidationException("text must not be blank");
        }
        if (StringUtils.length(text) > 120) {
            throw new ValidationException("text must not exceed 120 characters");
        }
    }

    public static void isNotEmptyPriority(Integer priority) {
        if (Objects.isNull(priority) || priority < 0 || priority > 3) {
            throw new ValidationException("priority must be between 0 and 3");
        }
    }
}
