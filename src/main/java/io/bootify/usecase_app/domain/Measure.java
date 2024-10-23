package io.bootify.usecase_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;


@Entity
public class Measure {

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
    private Long measureId;

    @Column
    private String measureName;

    @ManyToMany
    @JoinTable(
            name = "MeasureUseCase",
            joinColumns = @JoinColumn(name = "measureId"),
            inverseJoinColumns = @JoinColumn(name = "useCaseId")
    )
    private Set<UseCase> useCases;

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(final Long measureId) {
        this.measureId = measureId;
    }

    public String getMeasureName() {
        return measureName;
    }

    public void setMeasureName(final String measureName) {
        this.measureName = measureName;
    }

    public Set<UseCase> getUseCases() {
        return useCases;
    }

    public void setUseCases(final Set<UseCase> useCases) {
        this.useCases = useCases;
    }

}
