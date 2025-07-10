package com.example.v0.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ToDoDTO {

    private Long id;

    @NotBlank(message = "text must not be blank")
    private String text;

    @FutureOrPresent(message = "dueDate cannot be in the past")
    private LocalDate dueDate;

    private boolean doneFlag;

    @Min(value = 1) @Max(value = 5)
    private Integer priority;
}
