package io.bootify.usecase_app.service;

import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.domain.UserRecommendation;
import io.bootify.usecase_app.model.UserRecommendationDTO;
import io.bootify.usecase_app.repos.UseCaseRepository;
import io.bootify.usecase_app.repos.UserRecommendationRepository;
import io.bootify.usecase_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserRecommendationService {

    private final UserRecommendationRepository userRecommendationRepository;
    private final UseCaseRepository useCaseRepository;

    public UserRecommendationService(
            final UserRecommendationRepository userRecommendationRepository,
            final UseCaseRepository useCaseRepository) {
        this.userRecommendationRepository = userRecommendationRepository;
        this.useCaseRepository = useCaseRepository;
    }

    public List<UserRecommendationDTO> findAll() {
        final List<UserRecommendation> userRecommendations = userRecommendationRepository.findAll(Sort.by("recommendationId"));
        return userRecommendations.stream()
                .map(userRecommendation -> mapToDTO(userRecommendation, new UserRecommendationDTO()))
                .toList();
    }

    public UserRecommendationDTO get(final Long recommendationId) {
        return userRecommendationRepository.findById(recommendationId)
                .map(userRecommendation -> mapToDTO(userRecommendation, new UserRecommendationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserRecommendationDTO userRecommendationDTO) {
        final UserRecommendation userRecommendation = new UserRecommendation();
        mapToEntity(userRecommendationDTO, userRecommendation);
        return userRecommendationRepository.save(userRecommendation).getRecommendationId();
    }

    public void update(final Long recommendationId,
            final UserRecommendationDTO userRecommendationDTO) {
        final UserRecommendation userRecommendation = userRecommendationRepository.findById(recommendationId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userRecommendationDTO, userRecommendation);
        userRecommendationRepository.save(userRecommendation);
    }

    public void delete(final Long recommendationId) {
        userRecommendationRepository.deleteById(recommendationId);
    }

    private UserRecommendationDTO mapToDTO(final UserRecommendation userRecommendation,
            final UserRecommendationDTO userRecommendationDTO) {
        userRecommendationDTO.setRecommendationId(userRecommendation.getRecommendationId());
        userRecommendationDTO.setRecommendedUseCaseId(userRecommendation.getRecommendedUseCaseId());
        userRecommendationDTO.setUseCase(userRecommendation.getUseCase() == null ? null : userRecommendation.getUseCase().getUseCaseId());
        return userRecommendationDTO;
    }

    private UserRecommendation mapToEntity(final UserRecommendationDTO userRecommendationDTO,
            final UserRecommendation userRecommendation) {
        userRecommendation.setRecommendedUseCaseId(userRecommendationDTO.getRecommendedUseCaseId());
        final UseCase useCase = userRecommendationDTO.getUseCase() == null ? null : useCaseRepository.findById(userRecommendationDTO.getUseCase())
                .orElseThrow(() -> new NotFoundException("useCase not found"));
        userRecommendation.setUseCase(useCase);
        return userRecommendation;
    }

}
