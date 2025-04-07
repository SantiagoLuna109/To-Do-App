package com.example.v0.demo.service;

import com.example.v0.demo.dao.ToDoDAO;
import com.example.v0.demo.model.ToDo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ToDoServiceImpl {
    private final ToDoDAO todoDAO;
    public ToDoServiceImpl(ToDoDAO todoDAO){
        this.todoDAO = todoDAO;
    }
    public List<ToDo> findAll(){
        return todoDAO.findAll();
    }
    public ToDo add(ToDo toDo){
        return todoDAO.save(toDo);
    }
    public boolean delete(Long id){
        return todoDAO.delete(id);
    }
    public ToDo update(Long id, ToDo todo){
        return todoDAO.update(id, todo);
    }

}
