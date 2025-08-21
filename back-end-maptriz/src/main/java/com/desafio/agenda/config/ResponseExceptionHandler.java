package com.desafio.agenda.config;

import com.desafio.agenda.config.exception.ContactListNotFoundException;
import com.desafio.agenda.config.exception.ContactNotFoundException;
import com.desafio.agenda.config.exception.UserNotFoundException;
import com.desafio.agenda.controller.dto.ErrorMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<Object> handle(Exception ex) {
        log.error("Error in the request {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessageDTO(ex.getMessage()));
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            ContactListNotFoundException.class,
            ContactNotFoundException.class
    })
    public ResponseEntity<Object> handle(RuntimeException ex) {
        log.error("Error in the request {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessageDTO(ex.getMessage()));
    }


}
