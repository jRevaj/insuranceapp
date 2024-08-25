package com.netgrif.insuranceapp.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TravelInsuranceDTO extends ContractDTO {
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Is liability covered is required")
    private boolean liabilityCovered;

    @NotNull(message = "Is accident covered is required")
    private boolean accidentCovered;
}
