package com.desafio.agenda.repository;

import com.desafio.agenda.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
