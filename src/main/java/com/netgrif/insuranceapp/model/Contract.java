package com.netgrif.insuranceapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "contracts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contract_type")
public abstract class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractNumber;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "insured_id")
    private Insured insured;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
