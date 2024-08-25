package com.netgrif.insuranceapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("PROPERTY")
public class PropertyInsurance extends Contract {
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "property_address_id", referencedColumnName = "id")
    private Address propertyAddress;

    private BigDecimal propertyValue;
}
