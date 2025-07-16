package com.example.v0.demo.repository;

import com.example.v0.demo.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ToDoRepositoryTest {

    @Autowired
    private ToDoRepository repo;

    @Test
    @DisplayName("Persists and retrieves To-Dos via Spring Data JPA")
    void savesAndFinds() {
        ToDo created = repo.save(ToDo.builder().text("Persist me").priority(1).build());

        List<ToDo> all = repo.findAll();

        assertThat(all)
                .extracting(ToDo::getId)
                .contains(created.getId());
    }
}

