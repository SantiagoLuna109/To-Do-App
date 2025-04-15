package com.example.v0.demo.controller;

import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.model.ToDosResponse;
import com.example.v0.demo.service.ToDoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/todos")
public class ToDoControllerImpl implements ToDoController {

    private final ToDoServiceImpl toDoService;

    public ToDoControllerImpl(ToDoServiceImpl toDoService) {
        this.toDoService = toDoService;
    }

    @Override
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo toDo) {
        ToDo createToDo = toDoService.add(toDo);
        return new ResponseEntity<>(createToDo, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo toDo) {
        ToDo updateToDo = toDoService.update(id, toDo);
        if (updateToDo != null) {
            return ResponseEntity.ok(updateToDo);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id) {
        boolean deleted = toDoService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<ToDosResponse> getTodos(
            @RequestParam(required = false) Boolean done,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer priority,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {

        PageResponse<ToDo> response = toDoService.getToDos(done, name, priority, page, size, sortField, sortDir);
        Map<String, Object> metrics = toDoService.calculateMetrics(null, null, null);
        ToDosResponse toDosResponse = new ToDosResponse(response, metrics);
        return ResponseEntity.ok(toDosResponse);
    }

    @Override
    public ResponseEntity<ToDo> markAsDone(@PathVariable Long id) {
        ToDo marked = toDoService.markAsDone(id);
        return ResponseEntity.ok(marked);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getMetrics(@RequestParam(required = false) Boolean done,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(required = false) Integer priority) {
        Map<String, Object> metrics = toDoService.calculateMetrics(done, name, priority);
        return ResponseEntity.ok(metrics);
    }

    @Override
    public ResponseEntity<ToDo> markAsUndone(@PathVariable Long id) {
        ToDo marked = toDoService.markAsUndone(id);
        return ResponseEntity.ok(marked);
    }
}