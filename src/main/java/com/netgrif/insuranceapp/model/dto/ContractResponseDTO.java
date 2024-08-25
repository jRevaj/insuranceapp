package com.netgrif.insuranceapp.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PropertyInsuranceResponseDTO.class, name = "PROPERTY"),
        @JsonSubTypes.Type(value = TravelInsuranceResponseDTO.class, name = "TRAVEL")
})
@Data
public abstract class ContractResponseDTO {
    private Long contractNumber;
    private LocalDate createdAt;
}
