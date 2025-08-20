package com.desafio.agenda.controller.dto;


import com.desafio.agenda.model.Contact;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@Builder
public class CreateContactDTO {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String document;
    @NotEmpty
    private String phoneNumber;
    private String description;
    @NotEmpty
    private CreateAddressDTO createAddressDTO;


    public static CreateContactDTO mapToDTO(Contact contact) {
        return CreateContactDTO.builder()
                .id(contact.getId())
                .name(contact.getName())
                .document(contact.getDocument())
                .phoneNumber(contact.getPhoneNumber())
                .description(!ObjectUtils.isEmpty(contact.getDescription()) ? contact.getDescription() : null)
                .createAddressDTO(CreateAddressDTO.mapToDTO(contact.getAddress()))
                .build();
    }
}
