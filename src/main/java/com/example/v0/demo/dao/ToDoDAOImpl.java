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
        save(new ToDo("ToDo14",LocalDateTime.of(2025,4,23,23,56),false,2));
        save(new ToDo("ToDo15",LocalDateTime.now().plusDays(34),false,2));
        save(new ToDo("ToDo16",LocalDateTime.now().plusDays(56),false,2));
        save(new ToDo("ToDo17",null,false,2));
        save(new ToDo("ToDo18",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo19",LocalDateTime.of(2025,5,30,23,56),false,1));
        save(new ToDo("ToDo20",LocalDateTime.of(2025,6,14,23,56),false,1));
        save(new ToDo("ToDo21",null,false,3));
        save(new ToDo("ToDo22",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo23",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo24",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo25",null,false,2));
        save(new ToDo("ToDo26",null,false,2));
        save(new ToDo("ToDo27",LocalDateTime.of(2025,4,23,23,56),false,2));
        save(new ToDo("ToDo28",LocalDateTime.now().plusDays(34),false,2));
        save(new ToDo("ToDo29",LocalDateTime.now().plusDays(56),false,2));
        save(new ToDo("ToDo30",null,false,2));
        save(new ToDo("ToDo31",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo32",LocalDateTime.of(2025,5,30,23,56),false,1));
        save(new ToDo("ToDo33",LocalDateTime.of(2025,6,14,23,56),false,1));
        save(new ToDo("ToDo34",null,false,3));
        save(new ToDo("ToDo35",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo36",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo37",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo38",null,false,2));
        save(new ToDo("ToDo39",null,false,2));
        save(new ToDo("ToDo40",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo41",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo42",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo43",null,false,2));
        save(new ToDo("ToDo44",null,false,2));
        save(new ToDo("ToDo45",LocalDateTime.of(2025,5,30,23,56),false,1));
        save(new ToDo("ToDo46",LocalDateTime.of(2025,6,14,23,56),false,1));
        save(new ToDo("ToDo47",null,false,3));
        save(new ToDo("ToDo48",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo49",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo50",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo51",null,false,2));
        save(new ToDo("ToDo52",null,false,2));
        save(new ToDo("ToDo53",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo54",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo55",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo56",null,false,2));
        save(new ToDo("ToDo57",null,false,2));
        save(new ToDo("ToDo58",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo59",null,false,2));
        save(new ToDo("ToDo60",null,false,2));
        save(new ToDo("ToDo61",LocalDateTime.of(2025,3,22,23,56),false,2));
        save(new ToDo("ToDo62",LocalDateTime.now(),false,2));
        save(new ToDo("ToDo63",LocalDateTime.now().plusDays(3),false,2));
        save(new ToDo("ToDo64",null,false,2));
        save(new ToDo("ToDo65",null,false,2));
    }
}
