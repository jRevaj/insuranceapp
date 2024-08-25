package com.netgrif.insuranceapp.service;

import com.netgrif.insuranceapp.exception.ResourceNotFoundException;
import com.netgrif.insuranceapp.model.Contract;
import com.netgrif.insuranceapp.model.Insured;
import com.netgrif.insuranceapp.repository.ContractRepository;
import com.netgrif.insuranceapp.repository.InsuredRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InsuredServiceImpl implements InsuredService {
    private static final Logger log = LoggerFactory.getLogger(InsuredServiceImpl.class);

    private final InsuredRepository insuredRepository;
    private final ContractRepository contractRepository;

    @Autowired
    public InsuredServiceImpl(InsuredRepository insuredRepository, ContractRepository contractRepository) {
        this.insuredRepository = insuredRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Insured> getAllInsured() {
        return insuredRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public Long createInsured(Insured insured) {
        log.info("Creating insured: {}", insured);
        if (insured.getPermanentAddress() == null) {
            log.error("Permanent address is required");
            throw new IllegalArgumentException("Permanent address is required");
        }

        if (insured.getCorrespondenceAddress() == null) {
            log.warn("Correspondence address is not set, using permanent address");
            insured.setCorrespondenceAddress(insured.getPermanentAddress());
        }

        Insured savedInsured = insuredRepository.save(insured);
        log.info("Insured created: {}", savedInsured);
        return savedInsured.getId();
    }

    @Override
    public Insured getInsuredById(Long id) {
        return insuredRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insured with id " + id + " was not found"));
    }

    @Override
    public Long createContract(Long insuredId, Contract contract) {
        log.info("Creating contract: {}", contract);
        Insured insured = getInsuredById(insuredId);
        log.info("Insured found: {}", insured);
        contract.setInsured(insured);
        Contract savedContract = contractRepository.save(contract);
        log.info("Contract created: {}", savedContract);
        return savedContract.getContractNumber();
    }
}
