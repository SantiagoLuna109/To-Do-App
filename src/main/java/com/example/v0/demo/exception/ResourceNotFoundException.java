package com.example.v0.demo.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);//Cuando no neuntre un rescurso como un To Do por su id se lanzaa
    }
}
