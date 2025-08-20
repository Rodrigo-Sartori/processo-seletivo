package com.desafio.agenda.controller;


import com.desafio.agenda.controller.dto.CreateContactDTO;
import com.desafio.agenda.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    @GetMapping
    public ResponseEntity<List<CreateContactDTO>> findByUser(@RequestHeader("Authorization") String authorizationHeader) {
        var contacts = contactService.findByUser(authorizationHeader);
        return ResponseEntity.status(HttpStatus.OK).body(contacts);
    }

    @PostMapping
    public ResponseEntity<CreateContactDTO> saveContact(@RequestBody CreateContactDTO dto, @RequestHeader("Authorization") String authorizationHeader) {
        var savedContact = contactService.saveContact(dto, authorizationHeader);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContact(@PathVariable Long id, @RequestBody CreateContactDTO dto) {
        contactService.updateContact(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Contact updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Contact deleted");
    }

}
