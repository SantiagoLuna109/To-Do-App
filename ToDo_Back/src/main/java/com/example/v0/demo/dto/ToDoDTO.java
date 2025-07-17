package com.example.v0.demo.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ToDoDTO {

    private Long id;
    private String text;
    private Integer priority;
    private LocalDateTime dueDate;
    private Boolean doneFlag;
    private LocalDateTime doneDate;
}


