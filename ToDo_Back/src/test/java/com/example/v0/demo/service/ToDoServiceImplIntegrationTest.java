package com.example.v0.demo.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceImplIntegrationTest {

    private ToDoServiceImpl service;
    @Disabled("will be rewritten for Spring Data")
    /*@BeforeEach
    public void setup() {
        ToDoDAOImpl dao = new ToDoDAOImpl();
        dao.initData();
        service = new ToDoServiceImpl(dao);
    }

    @Test
    public void testGetToDosFilterByDoneTrue() {
        ToDo todo = service.getToDos(null, null, null, 0, 10, "id", "asc").getContent().get(0);
        todo.setDoneFlag(true);
        todo.setDoneDate(LocalDateTime.now());
        service.update(todo.getId(), todo);

        PageResponse<ToDo> response = service.getToDos(true, null, null, 0, 10, "id", "asc");
        response.getContent().forEach(t -> assertTrue(t.isDoneFlag()));
    }

    @Test
    public void testGetToDosOrderByTextAsc() {
        ToDo todoA = new ToDo("Alpha", LocalDateTime.now().plusDays(1), false, 2);
        todoA.setId(101L);
        todoA.setCreationDate(LocalDateTime.now().minusDays(1));

        ToDo todoB = new ToDo("Bravo", LocalDateTime.now().plusDays(2), false, 1);
        todoB.setId(102L);
        todoB.setCreationDate(LocalDateTime.now().minusDays(1));

        ToDo todoC = new ToDo("Charlie", LocalDateTime.now().plusDays(3), false, 3);
        todoC.setId(103L);
        todoC.setCreationDate(LocalDateTime.now().minusDays(1));

        service.add(todoA);
        service.add(todoB);
        service.add(todoC);

        PageResponse<ToDo> response = service.getToDos(null, null, null, 0, 10, "text", "asc");
        var filtered = response.getContent().stream()
                .filter(t -> t.getText().equals("Alpha") || t.getText().equals("Bravo") || t.getText().equals("Charlie")).toList();
        assertEquals("Alpha", filtered.get(0).getText());
        assertEquals("Bravo", filtered.get(1).getText());
        assertEquals("Charlie", filtered.get(2).getText());
    }

    @Test
    public void testMarkAsDoneAndUndone() {
        ToDo todo = service.getToDos(null, null, null, 0, 10, "id", "asc").getContent().get(0);
        ToDo doneTodo = service.markAsDone(todo.getId());
        assertTrue(doneTodo.isDoneFlag());
        assertNotNull(doneTodo.getDoneDate());

        ToDo undoneTodo = service.markAsUndone(todo.getId());
        assertFalse(undoneTodo.isDoneFlag());
        assertNull(undoneTodo.getDoneDate());
    }
    */
    @Test
    public void testCalculateMetrics() {
        Map<String, Object> metrics = service.calculateMetrics(null, null, null);
        assertNotNull(metrics);
        Number globalAverage = (Number) metrics.get("globalAverage");
        Map<String, Number> averageByPriority = (Map<String, Number>) metrics.get("averageTimesByPriority");
        Number totalFilteres = (Number) metrics.get("totalFilteres");

        assertNotNull(globalAverage);
        assertNotNull(averageByPriority);
        assertNotNull(totalFilteres);

    }
/*
    @Test
    public void testDelete() {
        ToDo newTodo = new ToDo("Test Delete", LocalDateTime.now().plusDays(1), false, 2);
        ToDo saved = service.add(newTodo);
        int initialSize = service.getToDos(null, null, null, 0, 100, "id", "asc").getContent().size();
        assertNotNull(saved);
        boolean deleted = service.delete(saved.getId());
        assertTrue(deleted);
        int afterSize = service.getToDos(null, null, null, 0, 100, "id", "asc").getContent().size();
        assertEquals(initialSize - 1, afterSize);
    }*/
}