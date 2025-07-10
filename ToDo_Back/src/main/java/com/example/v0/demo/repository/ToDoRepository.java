package com.example.v0.demo.repository;

import com.example.v0.demo.model.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ToDoRepository
        extends JpaRepository<ToDo, Long>,
        JpaSpecificationExecutor<ToDo> {

    Page<ToDo> findByDoneFlagAndPriority(
            boolean doneFlag,
            Integer priority,
            Pageable pageable);
}
