package com.example.v0.demo.service;

import com.example.v0.demo.dao.ToDoDAO;
import com.example.v0.demo.exception.ResourceNotFoundException;
import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        boolean deleted = todoDAO.delete(id);
        if(!deleted){
            throw new ResourceNotFoundException("No se ah encontrado un TODo con el id: " + id);
        }
        return deleted;
    }
    public ToDo update(Long id, ToDo todo){
        ToDo updated = todoDAO.update(id,todo);
        if(Objects.isNull(updated)){
            throw new ResourceNotFoundException("No se encontro el ToDo con el id: " + id);
        }
        return updated;
    }
    public PageResponse<ToDo> getToDos(Boolean done, int page, int size, String sortFiel, String sortDir){
        List<ToDo> todos = findAll();
        if (done != null){
            todos = todos.stream().filter(t -> t.isDoneFlag() == done).toList();
        }
        Comparator<ToDo> comparator;
        switch (sortFiel){
            case "text":
                comparator = Comparator.comparing(ToDo::getText, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
            case "dueDate":
                comparator = Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(LocalDateTime::compareTo));
                break;
            case "creationDate":
                comparator = Comparator.comparing(ToDo::getCreationDate, Comparator.nullsLast(LocalDateTime::compareTo));
                break;
            default:
                comparator = Comparator.comparing(ToDo::getId);
                break;
        }
        if ("desc".equalsIgnoreCase(sortDir)){
            comparator = comparator.reversed();
        }
        todos.sort(comparator);
        int totalElemnts = todos.size();
        int totalPages = (int) Math.ceil((double) totalElemnts / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElemnts);
        List<ToDo> paginated = fromIndex <toIndex ? todos.subList(fromIndex, toIndex) : List.of();
        return new PageResponse<>(paginated,page,totalPages,totalElemnts);
    }
}
