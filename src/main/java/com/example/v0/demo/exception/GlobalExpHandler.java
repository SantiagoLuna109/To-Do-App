package com.example.v0.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExpHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso No Encontrado",
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request){
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> validationErrors.put(err.getField(), err.getDefaultMessage()));

        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Fallo de Validacion",
                validationErrors.toString(),
                request.getDescription(false));
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> hadleAllExceptions(Exception ex,
                                                            WebRequest request){
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erorr interno del servidor",
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static class ErrorResponse {
        private LocalDateTime timeStamp;
        private int status;
        private String error;
        private String message;
        private String path;
        public ErrorResponse(LocalDateTime timeStamp, int status, String error, String message, String path){
            this.timeStamp = timeStamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }
        public LocalDateTime getTimeStamp(){
            return timeStamp;
        }
        public String getError(){
            return error;
        }
        public int getStatus() {
            return status;
        }
        public String getMessage(){
            return message;
        }
        public String getPath(){
            return path;
        }
    }
}
