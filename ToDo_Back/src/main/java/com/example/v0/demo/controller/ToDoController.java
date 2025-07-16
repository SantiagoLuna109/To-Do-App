package com.example.v0.demo.controller;

import com.example.v0.demo.dto.ToDoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ToDoController {

    ResponseEntity<Page<ToDoDTO>> getAll(Pageable pageable);
    ResponseEntity<ToDoDTO>       getById(Long id);
    ResponseEntity<ToDoDTO>       create(ToDoDTO dto);
    ResponseEntity<ToDoDTO>       update(Long id, ToDoDTO dto);
    ResponseEntity<ToDoDTO>       markDone(Long id);
    ResponseEntity<Void>          delete(Long id);
}
