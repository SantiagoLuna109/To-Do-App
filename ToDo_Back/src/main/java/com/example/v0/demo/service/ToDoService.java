package com.example.v0.demo.service;

import com.example.v0.demo.model.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ToDoService {
    List<ToDo> findAll();
    ToDo add(ToDo toDo);
    boolean delete(Long id);
    ToDo update(Long id, ToDo toDo);
    ToDo markAsDone(Long id);
    ToDo markAsUndone(Long id);
    Map<String, Object> calculateMetrics(Boolean done, String name, Integer priority);
    Page<ToDo> getToDos(Boolean done,
                        String name,
                        Integer priority,
                        Pageable pageable);
}
