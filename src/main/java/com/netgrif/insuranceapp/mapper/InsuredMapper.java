package com.netgrif.insuranceapp.mapper;

import com.netgrif.insuranceapp.model.Contract;
import com.netgrif.insuranceapp.model.Insured;
import com.netgrif.insuranceapp.model.dto.ContractResponseDTO;
import com.netgrif.insuranceapp.model.dto.InsuredBasicDTO;
import com.netgrif.insuranceapp.model.dto.InsuredDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {ContractMapper.class})
public interface InsuredMapper {

    InsuredBasicDTO insuredToInsuredBasicDTO(Insured insured);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "permanentAddress.id", ignore = true)
    @Mapping(target = "correspondenceAddress.id", ignore = true)
    Insured insuredBasicDTOToInsured(InsuredBasicDTO dto);

    @Mapping(target = "contracts", source = "contracts", qualifiedByName = "mapContractsToResponse")
    InsuredDetailDTO insuredToInsuredDetailDTO(Insured insured);

    @Named("mapContractsToResponse")
    default List<ContractResponseDTO> mapContractsToResponse(List<Contract> contracts) {
        return contracts.stream()
                .map(this::mapContractToResponse)
                .toList();
    }

    @Named("mapContractToResponse")
    default ContractResponseDTO mapContractToResponse(Contract contract) {
        return Mappers.getMapper(ContractMapper.class).contractToContractDTO(contract);
    }
}
