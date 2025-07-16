package com.example.v0.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToDoDTO {

    private Long id;

    @NotBlank(message = "Text must not be blank")
    private String text;

    private LocalDateTime dueDate;

    @Builder.Default
    private Boolean doneFlag = false;

    private LocalDateTime doneDate;

    @NotNull
    @Builder.Default
    private Integer priority = 3;
}

