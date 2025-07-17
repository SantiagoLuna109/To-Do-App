package com.example.v0.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    private LocalDateTime dueDate;

    private LocalDateTime doneDate;

    @Builder.Default
    private int priority = 3;

    @Builder.Default
    private boolean doneFlag = false;

    @NotBlank
    @Column(nullable = false)
    private String text;

    @PrePersist
    void onCreate() { this.creationDate = LocalDateTime.now(); }
}

