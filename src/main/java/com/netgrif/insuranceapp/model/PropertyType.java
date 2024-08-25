package com.netgrif.insuranceapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyType {
    APARTMENT("Byt"),
    BRICK_HOUSE("Rodinný dom - murovaný"),
    WOODEN_HOUSE("Rodinný dom - drevený");

    private final String description;

}
