package com.example.v0.demo.controller;

import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ToDoController {
    @GetMapping("/todos")
    ResponseEntity<List<ToDo>> getAllToDos();
    @PostMapping("")
    ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo);
    @PutMapping("/{id}")
    ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDo);
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteToDo(@PathVariable Long id);
    @GetMapping("")
    ResponseEntity<PageResponse<ToDo>> getTodos(@RequestParam(required = false) Boolean done,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) Integer priority,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortDir);
    @PostMapping("/{id}/done")
    ResponseEntity<ToDo> markAsDone(@PathVariable Long id);
    @PutMapping("/{id}/undone")
    ResponseEntity<ToDo> markAsUndone(@PathVariable Long id);
}
