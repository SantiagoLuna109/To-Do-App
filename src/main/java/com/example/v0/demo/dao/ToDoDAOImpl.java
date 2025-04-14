package com.example.v0.demo.dao;

import com.example.v0.demo.model.ToDo;
import jakarta.annotation.PostConstruct;
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
    @PostConstruct
    public void initData(){
        save(new ToDo("ToDo1",LocalDateTime.of(2025,4,23,23,56),false,2));
        save(new ToDo("ToDo2",LocalDateTime.now().plusDays(34),false,2));
        save(new ToDo("ToDo3",LocalDateTime.now().plusDays(56),false,2));
        save(new ToDo("ToDo4",null,false,2));
        save(new ToDo("ToDo11",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo5",LocalDateTime.of(2025,5,30,23,56),false,1));
        save(new ToDo("ToDo6",LocalDateTime.of(2025,6,14,23,56),false,1));
        save(new ToDo("ToDo7",null,false,3));
        save(new ToDo("ToDo8",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo9",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo10",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo12",null,false,2));
        save(new ToDo("ToDo13",null,false,2));
    }
}
