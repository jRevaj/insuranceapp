package com.netgrif.insuranceapp.model.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PropertyInsuranceDTO.class, name = "PROPERTY"),
        @JsonSubTypes.Type(value = TravelInsuranceDTO.class, name = "TRAVEL")
})
@Data
public abstract class ContractDTO {

}
