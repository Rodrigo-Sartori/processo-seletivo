package com.desafio.agenda.controller;

import com.desafio.agenda.controller.dto.EmailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @PostMapping
    public ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO) {
        System.out.printf("email received %s: %n", emailDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Email sent");
    }
}
