package com.example.v0.demo.controller;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.mapper.ToDoMapper;
import com.example.v0.demo.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ToDoControllerImpl implements ToDoController {

    private final ToDoService toDoService;
    private final ToDoMapper mapper;

    @Override
    public ResponseEntity<ToDoDTO> createToDo(ToDoDTO dto) {
        return ResponseEntity
                .status(201)
                .body(mapper.toDto(toDoService.add(mapper.toEntity(dto))));
    }

    @Override
    public ResponseEntity<ToDoDTO> updateToDo(Long id, ToDoDTO dto) {
        return ResponseEntity.ok(
                mapper.toDto(toDoService.update(id, mapper.toEntity(dto))));
    }

    @Override
    public ResponseEntity<Void> deleteToDo(Long id) {
        toDoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<ToDoDTO>> getTodos(Pageable pageable,
                                                  Boolean done,
                                                  String name,
                                                  Integer priority) {
        Page<ToDoDTO> page = mapper.toDtoPage(
                toDoService.getToDos(done, name, priority, pageable));

        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<ToDoDTO> markAsDone(Long id) {
        return ResponseEntity.ok(
                mapper.toDto(toDoService.markAsDone(id)));
    }

    @Override
    public ResponseEntity<ToDoDTO> markAsUndone(Long id) {
        return ResponseEntity.ok(
                mapper.toDto(toDoService.markAsUndone(id)));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getMetrics(Boolean done,
                                                          String name,
                                                          Integer priority) {
        return ResponseEntity.ok(
                toDoService.calculateMetrics(done, name, priority));
    }
}
