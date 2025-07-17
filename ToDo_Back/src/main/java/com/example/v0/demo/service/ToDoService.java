package com.example.v0.demo.service;

import com.example.v0.demo.dto.ToDoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToDoService {

    Page<ToDoDTO> findAll(Pageable pageable);
    ToDoDTO       findById(Long id);
    ToDoDTO       create(ToDoDTO dto);
    ToDoDTO       update(Long id, ToDoDTO dto);
    ToDoDTO       markDone(Long id);
    void          delete(Long id);
}

