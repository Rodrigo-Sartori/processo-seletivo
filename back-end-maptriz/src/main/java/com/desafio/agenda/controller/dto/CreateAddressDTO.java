package com.desafio.agenda.controller.dto;


import com.desafio.agenda.model.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
@Builder
public class CreateAddressDTO {

    @NotEmpty
    private String cep;
    @NotEmpty
    private String street;
    @NotEmpty
    private Long number;
    private String complement;
    @NotEmpty
    private String district;

    public static CreateAddressDTO mapToDTO(Address address) {
        return CreateAddressDTO.builder()
                .cep(address.getCep())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(!ObjectUtils.isEmpty(address.getComplement()) ? address.getComplement() : null)
                .district(address.getDistrict())
                .build();

    }

    public static Address maptoModel(CreateAddressDTO dto) {
        var address = new Address();
        address.setCep(dto.getCep());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());
        address.setDistrict(dto.getDistrict());
        address.setComplement(!ObjectUtils.isEmpty(dto.getComplement()) ? dto.getComplement() : null);
        return address;
    }
}
