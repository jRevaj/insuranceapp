package com.netgrif.insuranceapp.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TravelInsuranceResponseDTO extends ContractResponseDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean liabilityCovered;
    private boolean accidentCovered;
}
