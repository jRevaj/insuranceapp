package com.netgrif.insuranceapp.mapper;

import com.netgrif.insuranceapp.model.Contract;
import com.netgrif.insuranceapp.model.PropertyInsurance;
import com.netgrif.insuranceapp.model.TravelInsurance;
import com.netgrif.insuranceapp.model.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ContractMapper {

    @ObjectFactory
    default ContractResponseDTO createContractDTO(Contract contract) {
        if (contract instanceof PropertyInsurance) {
            return new PropertyInsuranceResponseDTO();
        } else if (contract instanceof TravelInsurance) {
            return new TravelInsuranceResponseDTO();
        }
        throw new IllegalArgumentException("Unknown contract type: " + contract.getClass());
    }

    ContractResponseDTO contractToContractDTO(Contract contract);

    @ObjectFactory
    default Contract createContract(ContractDTO dto) {
        if (dto instanceof PropertyInsuranceDTO) {
            return new PropertyInsurance();
        } else if (dto instanceof TravelInsuranceDTO) {
            return new TravelInsurance();
        }
        throw new IllegalArgumentException("Unknown contract DTO type: " + dto.getClass());
    }

    @Mapping(target = "contractNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "insured", ignore = true)
    Contract contractDTOToContract(ContractDTO dto);

    @AfterMapping
    default void setContractType(@MappingTarget Contract contract, ContractDTO dto) {
        // This method is called after mapping to ensure the correct type is set
        if (contract instanceof PropertyInsurance propertyInsurance && dto instanceof PropertyInsuranceDTO propertyInsuranceDTO) {
            propertyInsurance.setPropertyType(propertyInsuranceDTO.getPropertyType());
            propertyInsurance.setPropertyValue(propertyInsuranceDTO.getPropertyValue());
            propertyInsurance.setPropertyAddress(AddressMapper.INSTANCE.addressDTOToAddress(propertyInsuranceDTO.getPropertyAddress()));
        } else if (contract instanceof TravelInsurance travelInsurance && dto instanceof TravelInsuranceDTO travelInsuranceDTO) {
            travelInsurance.setStartDate(travelInsuranceDTO.getStartDate());
            travelInsurance.setEndDate(travelInsuranceDTO.getEndDate());
            travelInsurance.setLiabilityCovered(travelInsuranceDTO.isLiabilityCovered());
            travelInsurance.setAccidentCovered(travelInsuranceDTO.isAccidentCovered());
        }
    }

    @AfterMapping
    default void setContractResponseType(@MappingTarget ContractResponseDTO dto, Contract contract) {
        if (contract instanceof PropertyInsurance propertyInsurance && dto instanceof PropertyInsuranceResponseDTO responseDTO) {
            responseDTO.setPropertyType(propertyInsurance.getPropertyType());
            responseDTO.setPropertyValue(propertyInsurance.getPropertyValue());
            responseDTO.setPropertyAddress(AddressMapper.INSTANCE.addressToAddressDTO(propertyInsurance.getPropertyAddress()));
        } else if (contract instanceof TravelInsurance travelInsurance && dto instanceof TravelInsuranceResponseDTO responseDTO) {
            responseDTO.setStartDate(travelInsurance.getStartDate());
            responseDTO.setEndDate(travelInsurance.getEndDate());
            responseDTO.setLiabilityCovered(travelInsurance.isLiabilityCovered());
            responseDTO.setAccidentCovered(travelInsurance.isAccidentCovered());
        }
    }
}
