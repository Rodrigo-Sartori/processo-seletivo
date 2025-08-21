package com.desafio.agenda.service;


import com.desafio.agenda.config.FormatUtils;
import com.desafio.agenda.config.exception.ContactListNotFoundException;
import com.desafio.agenda.config.exception.ContactNotFoundException;
import com.desafio.agenda.controller.dto.CreateAddressDTO;
import com.desafio.agenda.controller.dto.CreateContactDTO;
import com.desafio.agenda.model.Address;
import com.desafio.agenda.model.Contact;
import com.desafio.agenda.repository.AddressRepository;
import com.desafio.agenda.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {


    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;

    @Transactional
    public List<CreateContactDTO> findByUser(String token) {
        var user = userService.getLogedUser(token);
        var savedContact = contactRepository.findByUser(user);
        if (savedContact.isEmpty()) {
            throw new ContactListNotFoundException("There are no contacts for this user");
        }
        return savedContact.stream().map(CreateContactDTO::mapToDTO).toList();
    }

    @Transactional
    public CreateContactDTO findByUserAndId(String token, Long id) {
        var user = userService.getLogedUser(token);
        var contact = contactRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ContactListNotFoundException("There are no contacts for this user"));
        return CreateContactDTO.mapToDTO(contact);
    }

    @Transactional
    public CreateContactDTO saveContact(CreateContactDTO dto, String token) {
        var address = saveAddress(dto.getCreateAddressDTO());
        var contact = mapContact(dto);
        contact.setAddress(address);
        var user = userService.getLogedUser(token);
        contact.setUser(user);
        var savedContact = contactRepository.save(contact);
        return CreateContactDTO.mapToDTO(savedContact);
    }

    @Transactional
    public void updateContact(Long id, CreateContactDTO dto) {
        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
        contact.setName(dto.getName());
        contact.setPhoneNumber(dto.getPhoneNumber());
        contact.setDescription(dto.getDescription());
        contact.setAddress(CreateAddressDTO.maptoModel(dto.getCreateAddressDTO()));
        contactRepository.save(contact);
    }

    @Transactional
    public void deleteContact(Long id) {
        var contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
        contactRepository.delete(contact);
    }

    private Contact mapContact(CreateContactDTO dto) {
        var contact = new Contact();
        contact.setName(dto.getName());
        contact.setDescription(!ObjectUtils.isEmpty(dto.getDescription()) ? dto.getDescription() : null);
        contact.setDocumentType(FormatUtils.validateDocument(dto.getDocument()));
        contact.setDocument(dto.getDocument());
        contact.setPhoneNumber(dto.getPhoneNumber());
        return contact;
    }

    private Address saveAddress(CreateAddressDTO dto) {
        FormatUtils.validateCEP(dto.getCep());
        var address = CreateAddressDTO.maptoModel(dto);
        return addressRepository.save(address);
    }

}
