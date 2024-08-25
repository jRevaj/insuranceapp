package com.netgrif.insuranceapp.controller;

import com.netgrif.insuranceapp.mapper.ContractMapper;
import com.netgrif.insuranceapp.mapper.InsuredMapper;
import com.netgrif.insuranceapp.model.Contract;
import com.netgrif.insuranceapp.model.Insured;
import com.netgrif.insuranceapp.model.dto.ContractDTO;
import com.netgrif.insuranceapp.model.dto.InsuredBasicDTO;
import com.netgrif.insuranceapp.model.dto.InsuredDetailDTO;
import com.netgrif.insuranceapp.service.InsuredService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/insured", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class InsuredController {

    private final InsuredService insuredService;
    private final InsuredMapper insuredMapper;
    private final ContractMapper contractMapper;

    @Autowired
    public InsuredController(InsuredService insuredService, InsuredMapper insuredMapper, ContractMapper contractMapper) {
        this.insuredService = insuredService;
        this.insuredMapper = insuredMapper;
        this.contractMapper = contractMapper;
    }

    @GetMapping
    public ResponseEntity<List<InsuredBasicDTO>> getAllInsured() {
        List<Insured> insuredList = insuredService.getAllInsured();
        List<InsuredBasicDTO> insuredDTOList = insuredList.stream().map(insuredMapper::insuredToInsuredBasicDTO).toList();
        return ResponseEntity.ok(insuredDTOList);
    }

    @PostMapping
    public ResponseEntity<Long> createInsured(@Valid @RequestBody InsuredBasicDTO insuredDTO) {
        Insured insured = insuredMapper.insuredBasicDTOToInsured(insuredDTO);
        Long insuredId = insuredService.createInsured(insured);
        return ResponseEntity.ok(insuredId);
    }

    @GetMapping("/{insuredId}")
    public ResponseEntity<InsuredDetailDTO> getInsuredById(@PathVariable Long insuredId) {
        Insured insured = insuredService.getInsuredById(insuredId);
        InsuredDetailDTO insuredDTO = insuredMapper.insuredToInsuredDetailDTO(insured);
        return ResponseEntity.ok(insuredDTO);
    }

    @PostMapping("/{insuredId}")
    public ResponseEntity<Long> createContract(@PathVariable Long insuredId, @Valid @RequestBody ContractDTO contractDTO) {
        Contract contract = contractMapper.contractDTOToContract(contractDTO);
        Long contractId = insuredService.createContract(insuredId, contract);
        return ResponseEntity.ok(contractId);
    }
}
