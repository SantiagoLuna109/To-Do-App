package com.example.v0.demo.controller;

import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.service.ToDoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todos")
@RestController
public class ToDoControllerImpl implements ToDoController{
    private final ToDoServiceImpl toDoService;
    public ToDoControllerImpl(ToDoServiceImpl toDoService){
        this.toDoService = toDoService;
    }
    @Override
    public ResponseEntity<List<ToDo>> getAllToDos(){
        List<ToDo> todos = toDoService.findAll();
        return ResponseEntity.ok(todos);
    }

    @Override
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo todo){
        ToDo createToDo = toDoService.add(todo);
        return new ResponseEntity<>(createToDo, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo todo){
        ToDo updateToDo = toDoService.update(id,todo);
        if(updateToDo != null){
            return ResponseEntity.ok(updateToDo);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id){
        boolean deleted = toDoService.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @Override
    public ResponseEntity<PageResponse<ToDo>> getTodos(@RequestParam(required = false) Boolean done,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) int priority,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "id") String sortField,
                                                       @RequestParam(defaultValue = "asc") String sortDir){
        PageResponse<ToDo> response = toDoService.getToDos(done, name, priority, page, size, sortField, sortDir);
        return  ResponseEntity.ok(response);
    }
    @Override
    public ResponseEntity<ToDo> markAsDone(@PathVariable Long id){
        ToDo marked = toDoService.markAsDone(id);
        return ResponseEntity.ok(marked);
    }
    @Override
    public ResponseEntity<ToDo> markAsUndone(@PathVariable Long id){
        ToDo marked = toDoService.markedAsUndone(id);
        return ResponseEntity.ok(marked);
    }
}
