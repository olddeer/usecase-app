package io.bootify.usecase_app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Getter
@Setter
public class Dimension {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long dimensionId;

    @Column
    private String dimensionName;

    @ManyToMany
    @JoinTable(
            name = "DimensionUseCase",
            joinColumns = @JoinColumn(name = "dimensionId"),
            inverseJoinColumns = @JoinColumn(name = "useCaseId")
    )
    private Set<UseCase> useCases;

}
