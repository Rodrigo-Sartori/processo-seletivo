package com.desafio.agenda.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "agenda_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;


    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;
}
