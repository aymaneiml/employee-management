package com.prj.employee_management.shared;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;



@ControllerAdvice
public class GlobalExceptionResponse {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalResponse<?>> handlerNoResourceException(NoResourceFoundException ex){
        //var herite le type de retour automtiquement
        var errors = List.of(
            new GlobalResponse.ErrorItem("Resource is not found!")
        );
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<GlobalResponse<?>> handleCustomResException(CustomResponseException ex){
        var errors = List.of(new GlobalResponse.ErrorItem(ex.getMessage()));
        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.resolve(ex.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<?>> handleValidationException(MethodArgumentNotValidException ex){
        var errors = ex.getBindingResult().getFieldErrors().stream()
            .map(err -> new GlobalResponse<>(err.getField() + " - " + err.getDefaultMessage()))
            .toList();

        return new ResponseEntity<>(new GlobalResponse<>(errors), HttpStatus.BAD_REQUEST);    
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<GlobalResponse<?>> handleDivisionByZeroException(ArithmeticException ex){
        var erros = List.of(
            new GlobalResponse.ErrorItem("divion par zero noooooo a 3chiri!")
        );

        return new ResponseEntity<>(new GlobalResponse<>(erros), HttpStatus.BAD_REQUEST);
    }

}

