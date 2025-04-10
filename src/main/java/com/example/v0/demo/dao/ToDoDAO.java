package com.example.v0.demo.dao;

import com.example.v0.demo.model.ToDo;

import java.util.List;

public interface ToDoDAO {
    List<ToDo> findAll();
    ToDo save(ToDo todo);
    ToDo update(Long id, ToDo todo);
    boolean delete(Long id);
}
