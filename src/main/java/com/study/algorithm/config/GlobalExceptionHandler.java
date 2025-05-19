package com.study.algorithm.config;


import com.study.algorithm.dto.other.MessageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageDTO> IllegalArgumentException(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body( new MessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MessageDTO> UsernameNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity.badRequest().body( new MessageDTO(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDTO> unCaughtException(Exception ex){
        return ResponseEntity.badRequest().body( new MessageDTO(ex.getMessage()));
    }
}

