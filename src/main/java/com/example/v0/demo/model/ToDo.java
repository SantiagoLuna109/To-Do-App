package com.example.v0.demo.model;

import java.time.*;
import org.springframework.util.StringUtils;
public class ToDo {
    private Long id;
    private String text;
    private LocalDateTime dueDate;
    private boolean doneFlag;
    private LocalDateTime doneDate;
    private int priority;
    private LocalDateTime creationDate;
    public ToDo(){}
    public ToDo(String text, LocalDateTime dueDate, boolean doneFlag, int priority){
        this.text = text;
        this.dueDate = dueDate;
        this.doneFlag = doneFlag;
        this.priority = priority;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }

    public LocalDateTime getDueDate(){
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;
    }

    public boolean isDoneFlag() {
        return doneFlag;
    }
    public void setDoneFlag(boolean doneFlag){
        this.doneFlag = doneFlag;
    }

    public int getPriority(){
        return priority;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }

    public LocalDateTime getDoneDate(){
        return doneDate;
    }
    public void setDoneDate(LocalDateTime doneDate){
        this.doneDate = doneDate;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
