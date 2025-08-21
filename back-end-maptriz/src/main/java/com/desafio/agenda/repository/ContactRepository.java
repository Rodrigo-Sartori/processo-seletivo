package com.desafio.agenda.repository;

import com.desafio.agenda.model.Contact;
import com.desafio.agenda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByUser(User user);
    Optional<Contact> findById(Long id);

    Optional<Contact> findByIdAndUser(Long id, User user);

}
