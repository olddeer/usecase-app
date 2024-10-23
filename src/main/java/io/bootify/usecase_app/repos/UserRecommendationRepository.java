package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.domain.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Long> {

    UserRecommendation findFirstByUseCase(UseCase useCase);

}
