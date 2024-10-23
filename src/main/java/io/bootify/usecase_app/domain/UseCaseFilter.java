package io.bootify.usecase_app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class UseCaseFilter {

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
    private Long useCaseFilterId;

    @Column
    private String filterOperation;

    @Column
    private String filterValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "use_case_id")
    private UseCase useCase;


}
