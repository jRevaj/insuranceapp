package com.netgrif.insuranceapp.model.dto;

import com.netgrif.insuranceapp.model.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class PropertyInsuranceDTO extends ContractDTO {
    @NotNull(message = "Property type is required")
    private PropertyType propertyType;

    @NotNull(message = "Property address is required")
    @Valid
    private AddressDTO propertyAddress;

    @NotNull(message = "Property value is required")
    @Positive(message = "Property value must be positive")
    private BigDecimal propertyValue;
}
