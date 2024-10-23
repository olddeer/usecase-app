package io.bootify.usecase_app.model;


public class UserRecommendationDTO {

    private Long recommendationId;
    private Long recommendedUseCaseId;
    private Long useCaseId;
    private Long useCase;

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

    public Long getUseCaseId() {
        return useCaseId;
    }

    public void setUseCaseId(final Long useCaseId) {
        this.useCaseId = useCaseId;
    }

    public Long getUseCase() {
        return useCase;
    }

    public void setUseCase(final Long useCase) {
        this.useCase = useCase;
    }

}
