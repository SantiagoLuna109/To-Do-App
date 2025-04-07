package com.example.v0.demo.controller;

import com.example.v0.demo.model.PageResponse;
import com.example.v0.demo.model.ToDo;
import com.example.v0.demo.service.ToDoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todos")
@RestController
public class ToDoControllerImpl implements ToDoController{//No olvidarse de hacer el implementn
    private final ToDoServiceImpl toDoService;
    public ToDoControllerImpl(ToDoServiceImpl toDoService){//Constructor por obra y gracia del espitiru santo
        this.toDoService = toDoService;
    }
    /*@GetMapping
    public List<To Do> getAllToDos(){
        return toDoService.findAll();// obtener toodo
    }*/
    @Override
    public ResponseEntity<List<ToDo>> getAllToDos(){
        List<ToDo> todos = toDoService.findAll();
        return ResponseEntity.ok(todos);
    }

    @Override
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo todo){
        ToDo createToDo = toDoService.add(todo);
        return new ResponseEntity<>(createToDo, HttpStatus.CREATED);//se crea la mafufada esta, osea new to do
    }

    //@PutMapping("/todos/{id}")
    @Override
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo todo){//actualizar un to do
        ToDo updateToDo = toDoService.update(id,todo);
        if(updateToDo != null){
            return ResponseEntity.ok(updateToDo);//si existe el to do ps se actualiza
        }
        return ResponseEntity.notFound().build();//aqui si solo dios sabe pk
    }

    //@DeleteMapping("/todos/{id}")
    @Override
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id){
        boolean deleted = toDoService.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();//Aqui checamos si ya fue eliminado
        }
        return ResponseEntity.notFound().build();
    }
    @Override
    public ResponseEntity<PageResponse<ToDo>> getTodos(@RequestParam(required = false) Boolean done,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "id") String sortField,
                                                       @RequestParam(defaultValue = "asc") String sortDir){
        PageResponse<ToDo> response = toDoService.getToDos(done, page, size, sortField, sortDir);
        return  ResponseEntity.ok(response);

    }
}
