package com.netgrif.insuranceapp.repository;

import com.netgrif.insuranceapp.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
