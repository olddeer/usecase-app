package io.bootify.usecase_app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;


@Entity
public class UserRecommendation {

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
    private Long recommendationId;

    @Column
    private Long recommendedUseCaseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "use_case_id")
    private UseCase useCase;

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(final Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public Long getRecommendedUseCaseId() {
        return recommendedUseCaseId;
    }

    public void setRecommendedUseCaseId(final Long recommendedUseCaseId) {
        this.recommendedUseCaseId = recommendedUseCaseId;
    }


    public UseCase getUseCase() {
        return useCase;
    }

    public void setUseCase(final UseCase useCase) {
        this.useCase = useCase;
    }

}
