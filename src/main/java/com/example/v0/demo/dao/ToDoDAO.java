package com.example.v0.demo.dao;

import com.example.v0.demo.model.ToDo;
import jakarta.annotation.PostConstruct;

import java.util.List;

public interface ToDoDAO {
    List<ToDo> findAll();
    ToDo save(ToDo todo);
    ToDo update(Long id, ToDo toDo);
    boolean delete(Long id);
}
