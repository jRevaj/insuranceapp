package com.netgrif.insuranceapp.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InsuredDetailDTO extends InsuredBasicDTO {
    private List<ContractResponseDTO> contracts;
}
