package com.example.v0.demo.controller;

import com.example.v0.demo.dto.ToDoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/todos")
public interface ToDoController {

    @PostMapping
    ResponseEntity<ToDoDTO> createToDo(@RequestBody ToDoDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<ToDoDTO> updateToDo(@PathVariable Long id, @RequestBody ToDoDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteToDo(@PathVariable Long id);

    @GetMapping
    ResponseEntity<Page<ToDoDTO>> getTodos(
            Pageable pageable,
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer priority);

    @PostMapping("/{id}/done")
    ResponseEntity<ToDoDTO> markAsDone(@PathVariable Long id);

    @PostMapping("/{id}/undone")
    ResponseEntity<ToDoDTO> markAsUndone(@PathVariable Long id);

    @GetMapping("/metrics")
    ResponseEntity<Map<String, Object>> getMetrics(
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer priority);
}
