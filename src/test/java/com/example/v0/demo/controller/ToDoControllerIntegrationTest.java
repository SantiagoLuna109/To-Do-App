package com.example.v0.demo.controller;

import com.example.v0.demo.model.ToDo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetTodosEndpoint() throws Exception {
        mockMvc.perform(get("/todos").param("page", "0").param("size", "10").param("sortField", "id")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageResponse").exists())
                .andExpect(jsonPath("$.metrics").exists());
    }

    @Test
    public void testCreateToDoEndpoint() throws Exception {
        ToDo newTodo = new ToDo("Test ToDo", null, false, 2);
        String newTodoJson = objectMapper.writeValueAsString(newTodo);

        mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(newTodoJson))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text", is("Test ToDo")));
    }

    @Test
    public void testUpdateToDoEndpoint() throws Exception {
        ToDo newTodo = new ToDo("ToDo Update", null, false, 2);
        String newTodoJson = objectMapper.writeValueAsString(newTodo);

        String createdJson = mockMvc.perform(post("/todos").contentType(MediaType.APPLICATION_JSON).content(newTodoJson))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ToDo createdTodo = objectMapper.readValue(createdJson, ToDo.class);

        createdTodo.setText("ToDo Updated");
        createdTodo.setDoneFlag(true);
        createdTodo.setDoneDate(java.time.LocalDateTime.now());
        String updatedJson = objectMapper.writeValueAsString(createdTodo);

        mockMvc.perform(put("/todos/" + createdTodo.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(updatedJson)).andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is("ToDo Updated")))
                .andExpect(jsonPath("$.doneFlag", is(true)));
    }

    @Test
    public void testDeleteToDoEndpoint() throws Exception {
        ToDo newTodo = new ToDo("ToDo to Delete", null, false, 2);
        String newTodoJson = objectMapper.writeValueAsString(newTodo);

        String createdJson = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON).content(newTodoJson)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        ToDo createdTodo = objectMapper.readValue(createdJson, ToDo.class);

        mockMvc.perform(delete("/todos/" + createdTodo.getId())).andExpect(status().isNoContent());
    }
}