package com.netgrif.insuranceapp.mapper;

import com.netgrif.insuranceapp.model.Address;
import com.netgrif.insuranceapp.model.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO addressToAddressDTO(Address address);

    @Mapping(target = "id", ignore = true)
    Address addressDTOToAddress(AddressDTO addressDTO);
}
