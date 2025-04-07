package com.example.v0.demo.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.*;

public class ToDo {
    private Long id;//Este se va a autocaluar espero :p
    @NotBlank(message = "El texto es si o si mano")//obligar a que ponga algo
    @NotNull(message = "Requerida manito")
    @Size(max = 120, min = 1, message = "No mas de 120 caracteres y/o al menos uno")//Definir el tamano max y mandar mensaje erroneo :p
    private String text;
    private LocalDateTime dueDate;//Teiene mas usos o mas cositas
    private boolean doneFlag;
    private LocalDateTime doneDate;
    @NotNull(message = "Requerida manito")
    @NotBlank(message = "El texto es si o si mano")
    private int priority;//Deberia de alguna manera validar el minimo y maximo?
    private LocalDateTime creationDate;
    /*
    * Id. This could be a number or string or a combination. Must be unique. yep
    * Text (required). Max length is 120 chars.
    * A due date (optional).
    * Done/undone flag
    * A done date. When the “to do” is marked as done this date is set
    * Priority (required). Options: High, Medium and Low.
    * Creation date. //supongo que esta habra que autocalculara con la fecha en la que se de
    * */
    //Constructor de base, se pueden eliminar los setter y getter, anotaciones se pueden checar con libreria lombok
    public ToDo(){}
    public ToDo(String text, LocalDateTime dueDate, boolean doneFlag, int priority){
        this.text = text;//Limpiar o validar el texto de alguna manera?
        this.dueDate = dueDate;
        this.doneFlag = doneFlag;
        this.priority = priority;
    }
    //Getters y Setterzzz
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }

    public LocalDateTime getDueDate(){
        return dueDate;
    }
    public void setDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;//Redundante? Se puede modificar a futuro que no
    }

    public boolean isDoneFlag() {
        return doneFlag;
    }
    public void setDoneFlag(boolean doneFlag){
        this.doneFlag = doneFlag;
    }

    public int getPriority(){
        return priority;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }

    public LocalDateTime getDoneDate(){
        return doneDate;
    }
    public void setDoneDate(LocalDateTime doneDate){
        this.doneDate = doneDate;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
