package com.netgrif.insuranceapp.mapper;

import com.netgrif.insuranceapp.model.Address;
import com.netgrif.insuranceapp.model.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void addressToAddressDTO_shouldMapAllFields() {
        Address address = new Address();
        address.setId(1L);
        address.setZipCode("12345");
        address.setCity("Test City");
        address.setStreet("Test Street");
        address.setHouseNumber("123");

        AddressDTO dto = addressMapper.addressToAddressDTO(address);

        assertNotNull(dto);
        assertEquals(address.getZipCode(), dto.getZipCode());
        assertEquals(address.getCity(), dto.getCity());
        assertEquals(address.getStreet(), dto.getStreet());
        assertEquals(address.getHouseNumber(), dto.getHouseNumber());
    }

    @Test
    void addressDTOToAddress_shouldMapAllFields() {
        AddressDTO dto = new AddressDTO();
        dto.setZipCode("54321");
        dto.setCity("Another City");
        dto.setStreet("Another Street");
        dto.setHouseNumber("321");

        Address address = addressMapper.addressDTOToAddress(dto);

        assertNotNull(address);
        assertNull(address.getId()); // ID should be ignored in mapping
        assertEquals(dto.getZipCode(), address.getZipCode());
        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getHouseNumber(), address.getHouseNumber());
    }
}