package com.netgrif.insuranceapp.service;

import com.netgrif.insuranceapp.exception.ResourceNotFoundException;
import com.netgrif.insuranceapp.model.*;
import com.netgrif.insuranceapp.repository.ContractRepository;
import com.netgrif.insuranceapp.repository.InsuredRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsuredServiceImplTest {

    @Mock
    private InsuredRepository insuredRepository;

    @Mock
    private ContractRepository contractRepository;

    private InsuredServiceImpl insuredService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        insuredService = new InsuredServiceImpl(insuredRepository, contractRepository);
    }

    @Test
    void getAllInsured_shouldReturnSortedList() {
        Insured insured1 = new Insured();
        insured1.setLastName("Doe");
        Insured insured2 = new Insured();
        insured2.setLastName("Smith");
        List<Insured> insuredList = Arrays.asList(insured1, insured2);

        when(insuredRepository.findAllByOrderByLastNameAsc()).thenReturn(insuredList);

        List<Insured> result = insuredService.getAllInsured();

        assertEquals(2, result.size());
        assertEquals("Doe", result.get(0).getLastName());
        assertEquals("Smith", result.get(1).getLastName());
        verify(insuredRepository, times(1)).findAllByOrderByLastNameAsc();
    }

    @Test
    void createInsured_shouldSaveAndReturnId() {
        Insured insured = new Insured();
        insured.setId(1L);
        insured.setFirstName("John");
        insured.setLastName("Doe");
        insured.setPersonalId("1234567890");
        insured.setEmail("test@test.test");
        insured.setPermanentAddress(new Address());

        when(insuredRepository.save(any(Insured.class))).thenReturn(insured);

        Long result = insuredService.createInsured(insured);

        assertNotNull(result);
        verify(insuredRepository, times(1)).save(insured);
    }

    @Test
    void getInsuredById_shouldReturnInsured() {
        Long id = 1L;
        Insured insured = new Insured();
        insured.setId(id);

        when(insuredRepository.findById(id)).thenReturn(Optional.of(insured));

        Insured result = insuredService.getInsuredById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(insuredRepository, times(1)).findById(id);
    }

    @Test
    void createInsured_withNullPermanentAddress_shouldThrowException() {
        Insured insured = new Insured();
        insured.setFirstName("John");
        insured.setLastName("Doe");
        insured.setPermanentAddress(null);

        assertThrows(IllegalArgumentException.class, () -> insuredService.createInsured(insured));
    }

    @Test
    void createInsured_withNullCorrespondenceAddress_shouldUsePermanentAddress() {
        Insured insured = new Insured();
        insured.setFirstName("John");
        insured.setLastName("Doe");
        Address permanentAddress = new Address();
        permanentAddress.setZipCode("12345");
        insured.setPermanentAddress(permanentAddress);
        insured.setCorrespondenceAddress(null);

        when(insuredRepository.save(any(Insured.class))).thenReturn(insured);

        insuredService.createInsured(insured);

        assertEquals(permanentAddress, insured.getCorrespondenceAddress());
        verify(insuredRepository, times(1)).save(insured);
    }

    @Test
    void getInsuredById_nonExistentId_shouldThrowResourceNotFoundException() {
        Long id = 999L;
        when(insuredRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> insuredService.getInsuredById(id));
    }

    @Test
    void createContract_propertyInsurance_shouldSaveAndReturnId() {
        Long insuredId = 1L;
        Insured insured = new Insured();
        insured.setId(insuredId);

        PropertyInsurance contract = new PropertyInsurance();
        contract.setPropertyType(PropertyType.APARTMENT);
        contract.setPropertyValue(BigDecimal.valueOf(100000));

        when(insuredRepository.findById(insuredId)).thenReturn(Optional.of(insured));
        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> {
            Contract savedContract = invocation.getArgument(0);
            savedContract.setContractNumber(1L);
            return savedContract;
        });

        Long contractId = insuredService.createContract(insuredId, contract);

        assertNotNull(contractId);
        assertEquals(1L, contractId);
        verify(contractRepository, times(1)).save(contract);
    }

    @Test
    void createContract_travelInsurance_shouldSaveAndReturnId() {
        Long insuredId = 1L;
        Insured insured = new Insured();
        insured.setId(insuredId);

        TravelInsurance contract = new TravelInsurance();
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusDays(7));
        contract.setLiabilityCovered(true);
        contract.setAccidentCovered(false);

        when(insuredRepository.findById(insuredId)).thenReturn(Optional.of(insured));
        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> {
            Contract savedContract = invocation.getArgument(0);
            savedContract.setContractNumber(2L);
            return savedContract;
        });

        Long contractId = insuredService.createContract(insuredId, contract);

        assertNotNull(contractId);
        assertEquals(2L, contractId);
        verify(contractRepository, times(1)).save(contract);
    }

    @Test
    void createContract_nonExistentInsured_shouldThrowResourceNotFoundException() {
        Long insuredId = 999L;
        Contract contract = new PropertyInsurance();

        when(insuredRepository.findById(insuredId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> insuredService.createContract(insuredId, contract));
    }
}