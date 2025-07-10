package com.example.v0.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String text;

    private LocalDateTime dueDate;

    @Column(nullable = false)
    private boolean doneFlag = false;

    private LocalDateTime doneDate;

    @Min(1) @Max(5)
    private Integer priority;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now();

    public void mergeFrom(ToDo src) {
        if (src.text != null)        this.text     = src.text;
        if (src.dueDate != null)     this.dueDate  = src.dueDate;
        if (src.priority != null)    this.priority = src.priority;
    }
}
