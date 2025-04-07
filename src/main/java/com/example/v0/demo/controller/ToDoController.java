package com.example.v0.demo.controller;

import com.example.v0.demo.model.PageResponse;
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
    @GetMapping
    ResponseEntity<PageResponse<ToDo>> getTodos(@RequestParam(required = false) Boolean done,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortDir);
}
