package io.bootify.usecase_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Entity
@Getter
@Setter
public class UseCase {

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
    private Long useCaseId;

    @Column
    private LocalDateTime publishedDateTime;

    @Column
    private LocalDateTime lastUpdatedDateTime;

    @OneToMany(mappedBy = "useCase")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "useCase")
    private Set<UseCaseFilter> useCaseFilters;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "useCase")
    private Set<UserRecommendation> userRecommendations;

    @ManyToMany(mappedBy = "useCases")
    private Set<Measure> measures;

    @ManyToMany(mappedBy = "useCases")
    private Set<Dimension> dimensions;

}
