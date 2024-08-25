package com.netgrif.insuranceapp.service;

import com.netgrif.insuranceapp.exception.ResourceNotFoundException;
import com.netgrif.insuranceapp.model.Contract;
import com.netgrif.insuranceapp.model.Insured;

import java.util.List;

/**
 * Service working with {@link Insured}s
 */
public interface InsuredService {

    /**
     * Retrieves all insured persons, sorted by last name in ascending order.
     *
     * @return A list of all insured persons.
     */
    List<Insured> getAllInsured();

    /**
     * Creates a new insured person.
     *
     * @param insured The insured person to be created.
     * @return The ID of the newly created insured person.
     */
    Long createInsured(Insured insured);

    /**
     * Retrieves an insured person by their ID.
     *
     * @param id The ID of the insured person to retrieve.
     * @return The insured person with the specified ID.
     * @throws ResourceNotFoundException if no insured person is found with the given ID.
     */
    Insured getInsuredById(Long id);

    /**
     * Creates a new contract for an insured person.
     *
     * @param insuredId The ID of the insured person.
     * @param contract  The contract to be created.
     * @return The ID of the newly created contract.
     * @throws ResourceNotFoundException if no insured person is found with the given ID.
     */
    Long createContract(Long insuredId, Contract contract);
}
