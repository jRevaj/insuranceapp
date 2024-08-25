package com.netgrif.insuranceapp.repository;

import com.netgrif.insuranceapp.model.Insured;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuredRepository extends JpaRepository<Insured, Long> {
    List<Insured> findAllByOrderByLastNameAsc();
}
