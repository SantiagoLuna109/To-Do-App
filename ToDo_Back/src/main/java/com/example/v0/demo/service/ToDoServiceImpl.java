package com.example.v0.demo.service;

import com.example.v0.demo.exception.ResourceNotFoundException;
import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.repository.ToDoRepository;
import com.example.v0.demo.util.Validator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ToDoServiceImpl implements  ToDoService{
    private final ToDoRepository repository;

    public ToDo add(@Valid ToDo toDo) {
        System.out.println("### Service received entity.text = " + toDo.getText()); // <--
        return repository.save(toDo);
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No ToDo with id: " + id);
        }
        repository.deleteById(id);
        return true;
    }

    public ToDo update(Long id, ToDo patch) {
        ToDo existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No ToDo with id: " + id));
        existing.mergeFrom(patch);
        return repository.save(existing);
    }

    public Page<ToDo> getToDos(Boolean done,
                               String name,
                               Integer priority,
                               Pageable pageable) {

        Specification<ToDo> spec = Specification.where(null);

        if (done != null)
            spec = spec.and((root, q, cb) -> cb.equal(root.get("doneFlag"), done));

        if (name != null && !name.isBlank())
            spec = spec.and((root, q, cb) -> cb.like(
                    cb.lower(root.get("text")), "%" + name.toLowerCase() + "%"));

        if (priority != null)
            spec = spec.and((root, q, cb) -> cb.equal(root.get("priority"), priority));

        return repository.findAll(spec, pageable);
    }

    public ToDo markAsDone(Long id) {
        ToDo t = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No ToDo with id: " + id));
        if (!t.isDoneFlag()) {
            t.setDoneFlag(true);
            t.setDoneDate(LocalDateTime.now());
            repository.save(t);
        }
        return t;
    }

    public ToDo markAsUndone(Long id) {
        ToDo t = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No ToDo with id: " + id));
        if (t.isDoneFlag()) {
            t.setDoneFlag(false);
            t.setDoneDate(null);
            repository.save(t);
        }
        return t;
    }
    public List<ToDo> findAll() {
        return repository.findAll();
    }
    public Map<String, Object> calculateMetrics(Boolean done, String name, Integer priority){
        List<ToDo> toDos = findAll();
        if(done != null){
            toDos = toDos.stream().filter(t ->t.isDoneFlag() == done).collect(Collectors.toList());
        }
        if(name!=null && !name.isEmpty()){
            toDos = toDos.stream().filter(t->t.getText()!=null && t.getText().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        }
        if(priority!=null){
            toDos = toDos.stream().filter(t->Objects.equals(t.getPriority(),priority)).collect(Collectors.toList());
        }
        Map<Integer, List<ToDo>> grouped = toDos.stream().filter(t->t.isDoneFlag() && t.getCreationDate() != null && t.getDoneDate() != null).collect(Collectors.groupingBy(ToDo::getPriority));
        Map<Integer, Long> averageTimes = new HashMap<>();
        for(Map.Entry<Integer, List<ToDo>> entry: grouped.entrySet()){
            long avg = Math.round(entry.getValue().stream().mapToLong(t-> Duration.between(t.getCreationDate(),t.getDoneDate()).toMinutes()).average().orElse(0));
            averageTimes.put(entry.getKey(),avg);
        }
        long globalAvg = Math.round(toDos.stream().filter(t->t.isDoneFlag() && t.getCreationDate() != null && t.getDoneDate() != null).mapToLong(t->Duration.between(t.getCreationDate(), t.getDoneDate()).toMinutes()).average().orElse(0));
        Map<String, Object> result = new HashMap<>();
        result.put("averageTimesByPriority",averageTimes);
        result.put("globalAverage",globalAvg);
        result.put("totalFilteres",toDos.size());
        return result;
    }
}
