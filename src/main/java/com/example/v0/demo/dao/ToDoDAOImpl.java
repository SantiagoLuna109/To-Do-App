package com.example.v0.demo.dao;

import com.example.v0.demo.model.ToDo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class ToDoDAOImpl implements ToDoDAO{
    private final Map<Long, ToDo> todos = new ConcurrentHashMap<>();
    private final AtomicLong idCnt = new AtomicLong();

    @Override
    public List<ToDo> findAll(){
        return new ArrayList<>(todos.values());
    }
    @Override
    public ToDo save(ToDo todo){
        Long id = idCnt.incrementAndGet();
        todo.setId(id);
        todo.setCreationDate(LocalDateTime.now());
        if (todo.isDoneFlag()){
            todo.setDoneDate(LocalDateTime.now());
        }
        todos.put(id, todo);
        return todo;
    }
    @Override
    public boolean delete(Long id){
        return todos.remove(id) != null;
    }
    @Override
    public ToDo update(Long id, ToDo todo){
        ToDo actualToDo = todos.get(id);
        if (actualToDo != null){
            actualToDo.setDueDate(todo.getDueDate());
            actualToDo.setText(todo.getText());
            if (!actualToDo.isDoneFlag() && todo.isDoneFlag()){
                actualToDo.setDoneFlag(true);
                actualToDo.setDoneDate(LocalDateTime.now());
            } else if(!todo.isDoneFlag()){
                actualToDo.setDoneDate(null);
                actualToDo.setDoneFlag(false);
            }
            actualToDo.setPriority(todo.getPriority());
            todos.put(id, actualToDo);
            return actualToDo;
        }
        return null;
    }
}
