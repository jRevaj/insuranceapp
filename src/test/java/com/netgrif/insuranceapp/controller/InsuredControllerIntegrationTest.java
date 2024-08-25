package com.netgrif.insuranceapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netgrif.insuranceapp.model.PropertyType;
import com.netgrif.insuranceapp.model.dto.AddressDTO;
import com.netgrif.insuranceapp.model.dto.InsuredBasicDTO;
import com.netgrif.insuranceapp.model.dto.PropertyInsuranceDTO;
import com.netgrif.insuranceapp.model.dto.TravelInsuranceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InsuredControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createInsured_shouldReturnCreatedId() throws Exception {
        InsuredBasicDTO insuredDTO = createValidInsuredDTO();

        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.matchesPattern("\\d+")));
    }

    @Test
    void getAllInsured_shouldReturnList() throws Exception {
        mockMvc.perform(get("/insured").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createInsured_withInvalidData_shouldReturnBadRequest() throws Exception {
        InsuredBasicDTO insuredDTO = new InsuredBasicDTO();

        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void createInsured_withValidDataAndCorrespondenceAddress_shouldReturnCreatedId() throws Exception {
        InsuredBasicDTO insuredDTO = createValidInsuredDTO();
        insuredDTO.setCorrespondenceAddress(createValidAddressDTO());

        mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.matchesPattern("\\d+")));
    }

    @Test
    void getInsuredById_existingId_shouldReturnInsured() throws Exception {
        InsuredBasicDTO insuredDTO = createValidInsuredDTO();
        MvcResult createResult = mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Long createdId = Long.parseLong(createResult.getResponse().getContentAsString());

        mockMvc.perform(get("/insured/{id}", createdId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.firstName").value(insuredDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(insuredDTO.getLastName()));
    }

    @Test
    void getInsuredById_nonExistentId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/insured/{id}", 999).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createPropertyInsuranceContract_shouldReturnCreatedId() throws Exception {
        InsuredBasicDTO insuredDTO = createValidInsuredDTO();
        MvcResult createInsuredResult = mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Long insuredId = Long.parseLong(createInsuredResult.getResponse().getContentAsString());

        PropertyInsuranceDTO contractDTO = new PropertyInsuranceDTO();
        contractDTO.setPropertyType(PropertyType.APARTMENT);
        contractDTO.setPropertyValue(BigDecimal.valueOf(100000));
        contractDTO.setPropertyAddress(createValidAddressDTO());

        mockMvc.perform(post("/insured/{id}", insuredId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.matchesPattern("\\d+")));
    }

    @Test
    void createTravelInsuranceContract_shouldReturnCreatedId() throws Exception {
        InsuredBasicDTO insuredDTO = createValidInsuredDTO();
        MvcResult createInsuredResult = mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Long insuredId = Long.parseLong(createInsuredResult.getResponse().getContentAsString());

        TravelInsuranceDTO contractDTO = new TravelInsuranceDTO();
        contractDTO.setStartDate(LocalDate.now());
        contractDTO.setEndDate(LocalDate.now().plusDays(7));
        contractDTO.setLiabilityCovered(true);
        contractDTO.setAccidentCovered(false);

        mockMvc.perform(post("/insured/{id}", insuredId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.matchesPattern("\\d+")));
    }

    @Test
    void createTravelInsuranceContract_withInvalidDates_shouldReturnBadRequest() throws Exception {
        InsuredBasicDTO insuredDTO = createValidInsuredDTO();
        MvcResult createInsuredResult = mockMvc.perform(post("/insured")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(insuredDTO)))
                .andExpect(status().isOk())
                .andReturn();

        Long insuredId = Long.parseLong(createInsuredResult.getResponse().getContentAsString());

        TravelInsuranceDTO contractDTO = new TravelInsuranceDTO();
        contractDTO.setStartDate(LocalDate.now().plusDays(7));
        contractDTO.setEndDate(LocalDate.now()); // End date before start date
        contractDTO.setLiabilityCovered(true);
        contractDTO.setAccidentCovered(false);

        mockMvc.perform(post("/insured/{id}", insuredId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contractDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    private InsuredBasicDTO createValidInsuredDTO() {
        InsuredBasicDTO insuredDTO = new InsuredBasicDTO();
        insuredDTO.setFirstName("John");
        insuredDTO.setLastName("Doe");
        insuredDTO.setPersonalId(String.valueOf(1000000000 + (long) (Math.random() * 1000000000)));
        insuredDTO.setEmail("john@example.com");
        insuredDTO.setPermanentAddress(createValidAddressDTO());
        return insuredDTO;
    }

    private AddressDTO createValidAddressDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZipCode("12345");
        addressDTO.setCity("Test City");
        addressDTO.setStreet("Test Street");
        addressDTO.setHouseNumber("123");
        return addressDTO;
    }
}