package com.example.v0.demo.service;

import com.example.v0.demo.dao.ToDoDAO;
import com.example.v0.demo.exception.ResourceNotFoundException;
import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ToDoServiceImpl {//LAS validaciones depnde
    private final ToDoDAO todoDAO;//era el ToDoDao
    public ToDoServiceImpl(ToDoDAO todoDAO){
        this.todoDAO = todoDAO;
    }
    public List<ToDo> findAll(){
        return todoDAO.findAll();
    }
    public ToDo add(ToDo toDo){
        return todoDAO.save(toDo);//Aqui parece contraproducente epro al ser el dao solo lo agregamos en si
    }
    public boolean delete(Long id){
        boolean deleted = todoDAO.delete(id);
        if(!deleted){
            throw new ResourceNotFoundException("No se ah encontrado un TODo con el id: " + id);
        }
        return deleted;//todoDAO.deleted(id);
    }
    public ToDo update(Long id, ToDo todo){
        ToDo updated = todoDAO.update(id,todo);
        if(updated==null){
            throw new ResourceNotFoundException("No se encontro el ToDo con el id: " + id);//Tirando excepcion pk somos unos cracks
        }
        return updated;
    }
    public PageResponse<ToDo> getToDos(Boolean done, int page, int size, String sortFiel, String sortDir){
        List<ToDo> todos = findAll();
        if (done != null){//MUY IMPORTANTE PORQUE ASI VEMOS SI SE ESPECIFICA ALGO
            todos = todos.stream().filter(t -> t.isDoneFlag() == done).toList();//Aqui que fue puro tab ajaja casi, pero p
        }
        Comparator<ToDo> comparator;//Este ayuda mucho muchito uwu, ya que aqui ordenamos la lista en funcion de un campo (aunque tal vez haya que corregir para que sean varios
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
        if ("desc".equalsIgnoreCase(sortDir)){//invertir orden si descendente uwu
            comparator = comparator.reversed();
        }
        todos.sort(comparator);
        int totalElemnts = todos.size();
        int totalPages = (int) Math.ceil((double) totalElemnts / size);//Aqui delemitmanos en base al espacio de elementospara la pantalla
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElemnts);
        List<ToDo> paginated = fromIndex <toIndex ? todos.subList(fromIndex, toIndex) : List.of();
        return new PageResponse<>(paginated,page,totalPages,totalElemnts);
    }
}
