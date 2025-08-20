package com.desafio.agenda.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String phoneNumber;
    private String document;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "ADDRESS_ID", unique = true, nullable = false)
    private Address address;

}
