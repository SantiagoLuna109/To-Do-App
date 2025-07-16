package com.example.v0.demo.controller;

import com.example.v0.demo.dto.MetricsDTO;
import com.example.v0.demo.dto.ToDoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ToDoController {

    ResponseEntity<Page<ToDoDTO>> getAll(
            String text,
            Integer priority,
            Boolean done,
            Pageable pageable);
    ResponseEntity<ToDoDTO>       getById(Long id);
    ResponseEntity<ToDoDTO>       create(ToDoDTO dto);
    ResponseEntity<ToDoDTO>       update(Long id, ToDoDTO dto);
    ResponseEntity<ToDoDTO>       markDone(Long id);
    ResponseEntity<Void>          delete(Long id);
    ResponseEntity<MetricsDTO>    getMetrics(String text,
                                          Integer priority,
                                          Boolean done);
}
