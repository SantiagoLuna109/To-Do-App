package com.example.v0.demo.service;

import com.example.v0.demo.dao.ToDoDAO;
import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;

import java.util.List;

public interface ToDoService {
    ToDoDAO ToDoService(ToDoDAO todoDAO);
    List<ToDo> findAll();
    ToDo add(ToDo toDo);
    boolean delete(Long id);
    ToDo update(Long id, ToDo todo);
    PageResponse<ToDo> getToDos(Boolean done, String name, int priority, int page, int size, String sortFiel, String sortDir);
    ToDo markAsDone(Long id);
    ToDo markAsUndone(Long id);
}
