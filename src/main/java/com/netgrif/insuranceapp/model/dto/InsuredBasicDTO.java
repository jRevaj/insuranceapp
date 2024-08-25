package com.netgrif.insuranceapp.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InsuredBasicDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Personal ID is required")
    @Pattern(regexp = "\\d{10}", message = "Personal ID must be 10 digits")
    private String personalId;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Permanent address is required")
    @Valid
    private AddressDTO permanentAddress;

    @Valid
    private AddressDTO correspondenceAddress;
}
