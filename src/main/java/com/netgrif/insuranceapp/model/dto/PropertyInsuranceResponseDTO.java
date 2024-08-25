package com.netgrif.insuranceapp.model.dto;

import com.netgrif.insuranceapp.model.PropertyType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class PropertyInsuranceResponseDTO extends ContractResponseDTO {
    private PropertyType propertyType;
    private AddressDTO propertyAddress;
    private BigDecimal propertyValue;
}
