package com.example.v0.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(
        name = "todos",
        indexes = {
                @Index(name = "idx_done_flag", columnList = "done_flag"),
                @Index(name = "idx_priority",  columnList = "priority")
        }
)
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "done_flag", nullable = false)
    private boolean doneFlag = false;

    @Column(name = "done_date")
    private LocalDateTime doneDate;

    @Column(nullable = false)
    private Integer priority = 3;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    protected ToDo() { }

    public ToDo(String text,
                LocalDateTime dueDate,
                boolean doneFlag,
                Integer priority) {
        this.text     = text;
        this.dueDate  = dueDate;
        this.doneFlag = doneFlag;
        this.priority = priority;
    }

    @PrePersist
    private void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDateTime.now();
        }
    }
    public void mergeFrom(ToDo src) {
        if (src.text != null)         this.text     = src.text;
        if (src.dueDate != null)      this.dueDate  = src.dueDate;
        if (src.priority != null)     this.priority = src.priority;
    }

}
