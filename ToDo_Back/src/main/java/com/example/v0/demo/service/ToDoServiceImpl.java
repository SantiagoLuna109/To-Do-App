package com.example.v0.demo.service;

import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.mapper.ToDoMapper;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repo;
    private final ToDoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ToDoDTO> findAll(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ToDoDTO findById(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("To-Do " + id + " not found")));
    }

    @Override
    public ToDoDTO create(ToDoDTO dto) {
        ToDo entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public ToDoDTO update(Long id, ToDoDTO dto) {
        ToDo entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("To-Do " + id + " not found"));
        mapper.update(entity, dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public ToDoDTO markDone(Long id) {
        ToDo entity = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("To-Do " + id + " not found"));
        entity.setDoneFlag(true);
        entity.setDoneDate(LocalDateTime.now());
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}

