package com.example.v0.demo.controller;

import com.example.v0.demo.model.ToDo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ToDoController {
    @GetMapping("/todos")
    ResponseEntity<List<ToDo>> getAllToDos();
    @PostMapping("/todos")
    ResponseEntity<ToDo> createToDo(@RequestBody ToDo todo);
    @PutMapping("/todos/{id}")
    ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo todo);
    @DeleteMapping("/todos/{id}")
    ResponseEntity<Void> deleteToDo(@PathVariable Long id);
}
