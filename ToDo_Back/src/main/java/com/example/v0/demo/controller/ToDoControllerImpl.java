package com.example.v0.demo.controller;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoControllerImpl implements ToDoController {

    private final ToDoService service;

    @Override
    @GetMapping
    public ResponseEntity<Page<ToDoDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ToDoDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<ToDoDTO> create(@RequestBody ToDoDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ToDoDTO> update(@PathVariable Long id, @RequestBody ToDoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Override
    @PutMapping("/{id}/done")
    public ResponseEntity<ToDoDTO> markDone(@PathVariable Long id) {
        return ResponseEntity.ok(service.markDone(id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

