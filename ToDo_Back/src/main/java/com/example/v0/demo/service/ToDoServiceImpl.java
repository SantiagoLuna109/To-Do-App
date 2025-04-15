package com.example.v0.demo.service;

import com.example.v0.demo.dao.ToDoDAO;
import com.example.v0.demo.exception.ResourceNotFoundException;
import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.util.Validator;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        Validator.isNotEmpty(toDo.getText());
        return todoDAO.save(toDo);
    }
    public boolean delete(Long id){
        boolean deleted = todoDAO.delete(id);
        if(!deleted){
            throw new ResourceNotFoundException("No ToDo with id: " + id);
        }
        return deleted;
    }
    public ToDo update(Long id, ToDo toDo){
        ToDo updated = todoDAO.update(id,toDo);
        if(Objects.isNull(updated)){
            throw new ResourceNotFoundException("No ToDo with id: " + id);
        }
        return updated;
    }
    public PageResponse<ToDo> getToDos(Boolean done, String name, Integer priority, int page, int size, String sortField, String sortDir){
        List<ToDo> toDos = findAll();
        if (done != null){
            toDos = toDos.stream().filter(t -> t.isDoneFlag() == done).collect((Collectors.toList()));
        }
        if (!Objects.isNull(name) && !name.isEmpty()){
            toDos = toDos.stream().filter(t -> !Objects.isNull(t.getText()) && t.getText().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        }
        if(!Objects.isNull(priority)){
            toDos = toDos.stream().filter(t-> Objects.equals(t.getPriority(), priority)).collect(Collectors.toList());
        }
        Comparator<ToDo> comparator;
        switch (sortField){
            case "text":
                comparator = Comparator.comparing(ToDo::getText, Comparator.nullsLast(String::compareToIgnoreCase));
                break;
            case "dueDate":
                comparator = Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(LocalDateTime::compareTo));
                break;
            case "priority":
                comparator = Comparator.comparing(ToDo::getPriority, Comparator.nullsLast(Integer::compareTo));
                break;
            default:
                comparator = Comparator.comparing(ToDo::getId);
                break;
        }
        if ("desc".equalsIgnoreCase(sortDir)){
            comparator = comparator.reversed();
        }
        toDos.sort(comparator);
        int totalElemnts = toDos.size();
        int totalPages = (int) Math.ceil((double) totalElemnts / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElemnts);
        List<ToDo> paginated = fromIndex <toIndex ? toDos.subList(fromIndex, toIndex) : List.of();
        return new PageResponse<>(paginated,page,totalPages,totalElemnts);
    }
    public ToDo markAsDone(Long id){
        ToDo toDo = findAll().stream().filter(t->t.getId().equals(id)).findFirst().orElseThrow(()-> new ResourceNotFoundException("No se encontro el ToDoo con el id: " + id));
        if(!toDo.isDoneFlag()){
            toDo.setDoneFlag(true);
            toDo.setDoneDate(LocalDateTime.now());
            todoDAO.update(id, toDo);
        }
        return toDo;
    }
    public ToDo markAsUndone(Long id){
        ToDo toDo = findAll().stream().filter(t->t.getId().equals(id)).findFirst().orElseThrow(()-> new ResourceNotFoundException("No se encontro el ToDoo con el id: " + id));
        if(toDo.isDoneFlag()){
            toDo.setDoneDate(null);
            toDo.setDoneFlag(false);
            todoDAO.update(id, toDo);
        }
        return toDo;
    }
    public Map<String, Object> calculateMetrics(Boolean done, String name, Integer priority){
        List<ToDo> toDos = findAll();
        if(done != null){
            toDos = toDos.stream().filter(t ->t.isDoneFlag() == done).collect(Collectors.toList());
        }
        if(name!=null && !name.isEmpty()){
            toDos = toDos.stream().filter(t->t.getText()!=null && t.getText().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        }
        if(priority!=null){
            toDos = toDos.stream().filter(t->Objects.equals(t.getPriority(),priority)).collect(Collectors.toList());
        }
        Map<Integer, List<ToDo>> grouped = toDos.stream().filter(t->t.isDoneFlag() && t.getCreationDate() != null && t.getDoneDate() != null).collect(Collectors.groupingBy(ToDo::getPriority));
        Map<Integer, Long> averageTimes = new HashMap<>();
        for(Map.Entry<Integer, List<ToDo>> entry: grouped.entrySet()){
            long avg = Math.round(entry.getValue().stream().mapToLong(t-> Duration.between(t.getCreationDate(),t.getDoneDate()).toMinutes()).average().orElse(0));
            averageTimes.put(entry.getKey(),avg);
        }
        long globalAvg = Math.round(toDos.stream().filter(t->t.isDoneFlag() && t.getCreationDate() != null && t.getDoneDate() != null).mapToLong(t->Duration.between(t.getCreationDate(), t.getDoneDate()).toMinutes()).average().orElse(0));
        Map<String, Object> result = new HashMap<>();
        result.put("averageTimesByPriority",averageTimes);
        result.put("globalAverage",globalAvg);
        result.put("totalFilteres",toDos.size());
        return result;
    }
}
