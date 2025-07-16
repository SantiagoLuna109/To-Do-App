package com.example.v0.demo.service;

import com.example.v0.demo.dto.MetricsDTO;
import com.example.v0.demo.dto.ToDoDTO;
import com.example.v0.demo.mapper.ToDoMapper;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository repo;
    private final ToDoMapper mapper;

    public Page<ToDoDTO> search(String text,
                                Integer priority,
                                Boolean done,
                                Pageable pageable) {

        Specification<ToDo> spec = Specification.where(null);

        if (StringUtils.hasText(text))
            spec = spec.and((r, q, cb) ->
                    cb.like(cb.lower(r.get("text")), "%" + text.toLowerCase() + "%"));

        if (priority != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("priority"), priority));

        if (done != null)
            spec = spec.and((r, q, cb) -> cb.equal(r.get("doneFlag"), done));

        return repo.findAll(spec, pageable).map(mapper::toDto);
    }


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

    @Override
    public MetricsDTO metrics(String text, Integer priority, Boolean done) {

        Specification<ToDo> spec = buildSpec(text, priority, done);

        List<ToDo> all = repo.findAll(spec);

        Map<Integer, List<Long>> byPrio = new HashMap<>();
        for (ToDo t : all) {
            if (!t.isDoneFlag()) continue;
            if (t.getDoneDate() == null || t.getDueDate() == null) continue;
            long diff = Math.abs(Duration
                    .between(t.getCreationDate(), t.getDoneDate())
                    .toMinutes());
            byPrio.computeIfAbsent(t.getPriority(), k -> new ArrayList<>())
                    .add(diff);
        }

        long totalCount = byPrio.values().stream().mapToLong(List::size).sum();
        long totalSum   = byPrio.values().stream()
                .flatMap(List::stream)
                .mapToLong(Long::longValue)
                .sum();
        double overallAvg = totalCount == 0 ? 0 : (double) totalSum / totalCount;

        Map<Integer, Double> avgByPrio = byPrio.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .mapToLong(Long::longValue)
                                .average()
                                .orElse(0.0)
                ));

        return new MetricsDTO(all.size(), overallAvg, avgByPrio);
    }

    private Specification<ToDo> buildSpec(String text,
                                          Integer priority,
                                          Boolean done) {
        Specification<ToDo> spec = Specification.where(null);

        if (StringUtils.hasText(text)) {
            spec = spec.and((root, q, cb) ->
                    cb.like(cb.lower(root.get("text")), "%" + text.toLowerCase() + "%"));
        }
        if (priority != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("priority"), priority));
        }
        if (done != null) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("doneFlag"), done));
        }
        return spec;
    }


}

