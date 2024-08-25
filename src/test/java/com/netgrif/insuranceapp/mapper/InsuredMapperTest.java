package com.netgrif.insuranceapp.mapper;

import com.netgrif.insuranceapp.model.*;
import com.netgrif.insuranceapp.model.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InsuredMapperTest {

    private InsuredMapper insuredMapper;

    @BeforeEach
    void setUp() {
        insuredMapper = Mappers.getMapper(InsuredMapper.class);
    }

    @Test
    void insuredToInsuredBasicDTO_shouldMapAllBasicFields() {
        Insured insured = createSampleInsured();

        InsuredBasicDTO dto = insuredMapper.insuredToInsuredBasicDTO(insured);

        assertNotNull(dto);
        assertEquals(insured.getId(), dto.getId());
        assertEquals(insured.getFirstName(), dto.getFirstName());
        assertEquals(insured.getLastName(), dto.getLastName());
        assertEquals(insured.getPersonalId(), dto.getPersonalId());
        assertEquals(insured.getEmail(), dto.getEmail());
        assertAddressEquals(insured.getPermanentAddress(), dto.getPermanentAddress());
        assertAddressEquals(insured.getCorrespondenceAddress(), dto.getCorrespondenceAddress());
    }

    @Test
    void insuredBasicDTOToInsured_shouldMapAllBasicFields() {
        InsuredBasicDTO dto = createSampleInsuredBasicDTO();

        Insured insured = insuredMapper.insuredBasicDTOToInsured(dto);

        assertNotNull(insured);
        assertNull(insured.getId());
        assertEquals(dto.getFirstName(), insured.getFirstName());
        assertEquals(dto.getLastName(), insured.getLastName());
        assertEquals(dto.getPersonalId(), insured.getPersonalId());
        assertEquals(dto.getEmail(), insured.getEmail());
        assertAddressEquals(dto.getPermanentAddress(), insured.getPermanentAddress());
        assertAddressEquals(dto.getCorrespondenceAddress(), insured.getCorrespondenceAddress());
        assertNull(insured.getContracts());
    }

    @Test
    void insuredToInsuredDetailDTO_shouldMapAllFieldsIncludingContracts() {
        Insured insured = createSampleInsured();
        insured.setContracts(Arrays.asList(
                createSamplePropertyInsurance(),
                createSampleTravelInsurance()
        ));

        InsuredDetailDTO dto = insuredMapper.insuredToInsuredDetailDTO(insured);

        assertNotNull(dto);
        assertEquals(insured.getId(), dto.getId());
        assertEquals(insured.getFirstName(), dto.getFirstName());
        assertEquals(insured.getLastName(), dto.getLastName());
        assertEquals(insured.getPersonalId(), dto.getPersonalId());
        assertEquals(insured.getEmail(), dto.getEmail());
        assertAddressEquals(insured.getPermanentAddress(), dto.getPermanentAddress());
        assertAddressEquals(insured.getCorrespondenceAddress(), dto.getCorrespondenceAddress());

        assertNotNull(dto.getContracts());
        assertEquals(2, dto.getContracts().size());

        ContractResponseDTO propertyContractDTO = dto.getContracts().get(0);
        assertInstanceOf(PropertyInsuranceResponseDTO.class, propertyContractDTO);
        PropertyInsuranceResponseDTO propertyInsuranceDTO = (PropertyInsuranceResponseDTO) propertyContractDTO;
        assertEquals(PropertyType.APARTMENT, propertyInsuranceDTO.getPropertyType());

        ContractResponseDTO travelContractDTO = dto.getContracts().get(1);
        assertInstanceOf(TravelInsuranceResponseDTO.class, travelContractDTO);
        TravelInsuranceResponseDTO travelInsuranceDTO = (TravelInsuranceResponseDTO) travelContractDTO;
        assertTrue(travelInsuranceDTO.isLiabilityCovered());
    }

    private Insured createSampleInsured() {
        Insured insured = new Insured();
        insured.setId(1L);
        insured.setFirstName("John");
        insured.setLastName("Doe");
        insured.setPersonalId("1234567890");
        insured.setEmail("john.doe@example.com");
        insured.setPermanentAddress(createSampleAddress());
        insured.setCorrespondenceAddress(createSampleAddress());
        return insured;
    }

    private Address createSampleAddress() {
        Address address = new Address();
        address.setId(1L);
        address.setZipCode("12345");
        address.setCity("Sample City");
        address.setStreet("Sample Street");
        address.setHouseNumber("123");
        return address;
    }

    private InsuredBasicDTO createSampleInsuredBasicDTO() {
        InsuredBasicDTO dto = new InsuredBasicDTO();
        dto.setFirstName("Jane");
        dto.setLastName("Smith");
        dto.setPersonalId("0987654321");
        dto.setEmail("jane.smith@example.com");
        dto.setPermanentAddress(createSampleAddressDTO());
        dto.setCorrespondenceAddress(createSampleAddressDTO());
        return dto;
    }

    private AddressDTO createSampleAddressDTO() {
        AddressDTO dto = new AddressDTO();
        dto.setZipCode("54321");
        dto.setCity("Another City");
        dto.setStreet("Another Street");
        dto.setHouseNumber("321");
        return dto;
    }

    private PropertyInsurance createSamplePropertyInsurance() {
        PropertyInsurance insurance = new PropertyInsurance();
        insurance.setContractNumber(1L);
        insurance.setCreatedAt(LocalDate.now());
        insurance.setPropertyType(PropertyType.APARTMENT);
        insurance.setPropertyValue(BigDecimal.valueOf(100000));
        insurance.setPropertyAddress(createSampleAddress());
        return insurance;
    }

    private TravelInsurance createSampleTravelInsurance() {
        TravelInsurance insurance = new TravelInsurance();
        insurance.setContractNumber(2L);
        insurance.setCreatedAt(LocalDate.now());
        insurance.setStartDate(LocalDate.now());
        insurance.setEndDate(LocalDate.now().plusDays(7));
        insurance.setLiabilityCovered(true);
        insurance.setAccidentCovered(false);
        return insurance;
    }

    private void assertAddressEquals(Address address, AddressDTO dto) {
        assertNotNull(address);
        assertNotNull(dto);
        assertEquals(address.getZipCode(), dto.getZipCode());
        assertEquals(address.getCity(), dto.getCity());
        assertEquals(address.getStreet(), dto.getStreet());
        assertEquals(address.getHouseNumber(), dto.getHouseNumber());
    }

    private void assertAddressEquals(AddressDTO dto, Address address) {
        assertNotNull(dto);
        assertNotNull(address);
        assertEquals(dto.getZipCode(), address.getZipCode());
        assertEquals(dto.getCity(), address.getCity());
        assertEquals(dto.getStreet(), address.getStreet());
        assertEquals(dto.getHouseNumber(), address.getHouseNumber());
    }
}