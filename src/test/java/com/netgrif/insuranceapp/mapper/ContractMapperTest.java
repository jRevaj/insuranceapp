package com.netgrif.insuranceapp.mapper;

import com.netgrif.insuranceapp.model.*;
import com.netgrif.insuranceapp.model.dto.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContractMapperTest {

    private final ContractMapper contractMapper = Mappers.getMapper(ContractMapper.class);

    @Test
    void propertyInsuranceToDTO_shouldMapAllFields() {
        PropertyInsurance insurance = new PropertyInsurance();
        insurance.setContractNumber(1L);
        insurance.setCreatedAt(LocalDate.now());
        insurance.setPropertyType(PropertyType.APARTMENT);
        insurance.setPropertyValue(BigDecimal.valueOf(100000));
        Address address = new Address();
        address.setZipCode("12345");
        address.setCity("Test City");
        address.setStreet("Test Street");
        address.setHouseNumber("123");
        insurance.setPropertyAddress(address);

        ContractResponseDTO dto = contractMapper.contractToContractDTO(insurance);

        assertInstanceOf(PropertyInsuranceResponseDTO.class, dto);
        PropertyInsuranceResponseDTO propertyDto = (PropertyInsuranceResponseDTO) dto;
        assertEquals(insurance.getContractNumber(), propertyDto.getContractNumber());
        assertEquals(insurance.getCreatedAt(), propertyDto.getCreatedAt());
        assertEquals(insurance.getPropertyType(), propertyDto.getPropertyType());
        assertEquals(insurance.getPropertyValue(), propertyDto.getPropertyValue());
        assertNotNull(propertyDto.getPropertyAddress());
        assertEquals(address.getZipCode(), propertyDto.getPropertyAddress().getZipCode());
    }

    @Test
    void travelInsuranceToDTO_shouldMapAllFields() {
        TravelInsurance insurance = new TravelInsurance();
        insurance.setContractNumber(2L);
        insurance.setCreatedAt(LocalDate.now());
        insurance.setStartDate(LocalDate.now());
        insurance.setEndDate(LocalDate.now().plusDays(7));
        insurance.setLiabilityCovered(true);
        insurance.setAccidentCovered(false);

        ContractResponseDTO dto = contractMapper.contractToContractDTO(insurance);

        assertInstanceOf(TravelInsuranceResponseDTO.class, dto);
        TravelInsuranceResponseDTO travelDto = (TravelInsuranceResponseDTO) dto;
        assertEquals(insurance.getContractNumber(), travelDto.getContractNumber());
        assertEquals(insurance.getCreatedAt(), travelDto.getCreatedAt());
        assertEquals(insurance.getStartDate(), travelDto.getStartDate());
        assertEquals(insurance.getEndDate(), travelDto.getEndDate());
        assertEquals(insurance.isLiabilityCovered(), travelDto.isLiabilityCovered());
        assertEquals(insurance.isAccidentCovered(), travelDto.isAccidentCovered());
    }

    @Test
    void propertyInsuranceDTOToEntity_shouldMapAllFields() {
        PropertyInsuranceDTO dto = new PropertyInsuranceDTO();
        dto.setPropertyType(PropertyType.BRICK_HOUSE);
        dto.setPropertyValue(BigDecimal.valueOf(200000));
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipCode("54321");
        addressDTO.setCity("Another City");
        addressDTO.setStreet("Another Street");
        addressDTO.setHouseNumber("321");
        dto.setPropertyAddress(addressDTO);

        Contract contract = contractMapper.contractDTOToContract(dto);

        assertInstanceOf(PropertyInsurance.class, contract);
        PropertyInsurance propertyInsurance = (PropertyInsurance) contract;
        assertNull(propertyInsurance.getContractNumber()); // Should be ignored in mapping
        assertNull(propertyInsurance.getCreatedAt()); // Should be ignored in mapping
        assertEquals(dto.getPropertyType(), propertyInsurance.getPropertyType());
        assertEquals(dto.getPropertyValue(), propertyInsurance.getPropertyValue());
        assertNotNull(propertyInsurance.getPropertyAddress());
        assertEquals(addressDTO.getZipCode(), propertyInsurance.getPropertyAddress().getZipCode());
    }

    @Test
    void travelInsuranceDTOToEntity_shouldMapAllFields() {
        TravelInsuranceDTO dto = new TravelInsuranceDTO();
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(14));
        dto.setLiabilityCovered(true);
        dto.setAccidentCovered(true);

        Contract contract = contractMapper.contractDTOToContract(dto);

        assertInstanceOf(TravelInsurance.class, contract);
        TravelInsurance travelInsurance = (TravelInsurance) contract;
        assertNull(travelInsurance.getContractNumber()); // Should be ignored in mapping
        assertNull(travelInsurance.getCreatedAt()); // Should be ignored in mapping
        assertEquals(dto.getStartDate(), travelInsurance.getStartDate());
        assertEquals(dto.getEndDate(), travelInsurance.getEndDate());
        assertEquals(dto.isLiabilityCovered(), travelInsurance.isLiabilityCovered());
        assertEquals(dto.isAccidentCovered(), travelInsurance.isAccidentCovered());
    }
}