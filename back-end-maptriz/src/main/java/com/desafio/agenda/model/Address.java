package com.desafio.agenda.model;

import com.desafio.agenda.controller.dto.CreateContactDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cep;

    private String street;

    private Long number;

    private String district;

    private String complement;

    @OneToOne(mappedBy = "address")
    private Contact contact;

}
