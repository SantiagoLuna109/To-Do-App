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
    private final Map<Long, ToDo> toDos = new ConcurrentHashMap<>();
    private final AtomicLong idCnt = new AtomicLong();

    @Override
    public List<ToDo> findAll(){
        return new ArrayList<>(toDos.values());
    }
    @Override
    public ToDo save(ToDo toDo){
        Long id = idCnt.incrementAndGet();
        toDo.setId(id);
        toDo.setCreationDate(LocalDateTime.now());
        if (toDo.isDoneFlag()){
            toDo.setDoneDate(LocalDateTime.now());
        }
        toDos.put(id, toDo);
        return toDo;
    }
    @Override
    public boolean delete(Long id){
        return toDos.remove(id) != null;
    }
    @Override
    public ToDo update(Long id, ToDo toDo){
        ToDo actualToDo = toDos.get(id);
        if (actualToDo != null){
            actualToDo.setDueDate(toDo.getDueDate());
            actualToDo.setText(toDo.getText());
            if (!actualToDo.isDoneFlag() && toDo.isDoneFlag()){
                actualToDo.setDoneFlag(true);
                actualToDo.setDoneDate(LocalDateTime.now());
            } else if(!toDo.isDoneFlag()){
                actualToDo.setDoneDate(null);
                actualToDo.setDoneFlag(false);
            }
            actualToDo.setPriority(toDo.getPriority());
            toDos.put(id, actualToDo);
            return actualToDo;
        }
        return null;
    }
}
