package io.bootify.usecase_app.service;

import io.bootify.usecase_app.domain.Comment;
import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.domain.User;
import io.bootify.usecase_app.domain.UserRecommendation;
import io.bootify.usecase_app.mapper.UseCaseMapper;
import io.bootify.usecase_app.model.UseCaseDTO;
import io.bootify.usecase_app.repos.CommentRepository;
import io.bootify.usecase_app.repos.MeasureRepository;
import io.bootify.usecase_app.repos.UseCaseRepository;
import io.bootify.usecase_app.repos.UserRecommendationRepository;
import io.bootify.usecase_app.repos.UserRepository;
import io.bootify.usecase_app.util.NotFoundException;
import io.bootify.usecase_app.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UseCaseService {

    private final UseCaseRepository useCaseRepository;
    private final UserRepository userRepository;
    private final MeasureRepository measureRepository;
    private final CommentRepository commentRepository;
    private final UserRecommendationRepository userRecommendationRepository;
    private final UseCaseMapper useCaseMapper;

    public UseCaseService(final UseCaseRepository useCaseRepository,
                          final UserRepository userRepository, final MeasureRepository measureRepository,
                          final CommentRepository commentRepository,
                          final UserRecommendationRepository userRecommendationRepository, UseCaseMapper useCaseMapper) {
        this.useCaseRepository = useCaseRepository;
        this.userRepository = userRepository;
        this.measureRepository = measureRepository;
        this.commentRepository = commentRepository;
        this.userRecommendationRepository = userRecommendationRepository;
        this.useCaseMapper = useCaseMapper;
    }

    public List<UseCaseDTO> findAll() {
        final List<UseCase> useCases = useCaseRepository.findAll(Sort.by("useCaseId"));
        return useCases.stream()
                .map(useCase -> mapToDTO(useCase, new UseCaseDTO()))
                .toList();
    }

    public UseCaseDTO get(final Long useCaseId) {
        return useCaseRepository.findById(useCaseId)
                .map(useCase -> mapToDTO(useCase, new UseCaseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UseCaseDTO useCaseDTO) {
        final UseCase useCase = new UseCase();
        mapToEntity(useCaseDTO, useCase);
        return useCaseRepository.save(useCase).getUseCaseId();
    }

    public void update(final Long useCaseId, final UseCaseDTO useCaseDTO) {
        final UseCase useCase = useCaseRepository.findById(useCaseId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(useCaseDTO, useCase);
        useCaseRepository.save(useCase);
    }

    public void delete(final Long useCaseId) {
        final UseCase useCase = useCaseRepository.findById(useCaseId)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        measureRepository.findAllByUseCases(useCase)
                .forEach(measure -> measure.getUseCases().remove(useCase));
        useCaseRepository.delete(useCase);
    }

    private UseCaseDTO mapToDTO(final UseCase useCase, final UseCaseDTO useCaseDTO) {
        useCaseDTO.setUseCaseId(useCase.getUseCaseId());
        useCaseDTO.setPublishedDateTime(useCase.getPublishedDateTime());
        useCaseDTO.setLastUpdatedDateTime(useCase.getLastUpdatedDateTime());
        useCaseDTO.setUserName(useCase.getUser() == null ? null : useCase.getUser().getUsername());
        useCaseDTO.setComments(useCaseMapper.toCommentDTOs(useCase.getComments()));
        useCaseDTO.setDimensions(useCaseMapper.toDimensionDTOs(useCase.getDimensions()));
        useCaseDTO.setMeasures(useCaseMapper.toMeasureDTOs(useCase.getMeasures()));
        useCaseDTO.setUseCaseFilters(useCaseMapper.toUseCaseFilterDTOs(useCase.getUseCaseFilters()));
        return useCaseDTO;
    }

    private UseCase mapToEntity(final UseCaseDTO useCaseDTO, final UseCase useCase) {
        useCase.setPublishedDateTime(useCaseDTO.getPublishedDateTime());
        useCase.setLastUpdatedDateTime(useCaseDTO.getLastUpdatedDateTime());
        final User user = useCaseDTO.getUserName() == null ? null : userRepository.findByUsername(useCaseDTO.getUserName())
                .orElseThrow(() -> new NotFoundException("user not found"));
        useCase.setUser(user);
        useCase.setMeasures(useCaseDTO.getMeasures());
        measureRepository.
        return useCase;
    }

    public ReferencedWarning getReferencedWarning(final Long useCaseId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final UseCase useCase = useCaseRepository.findById(useCaseId)
                .orElseThrow(NotFoundException::new);
        final Comment useCaseComment = commentRepository.findFirstByUseCase(useCase);
        if (useCaseComment != null) {
            referencedWarning.setKey("useCase.comment.useCase.referenced");
            referencedWarning.addParam(useCaseComment.getCommentId());
            return referencedWarning;
        }
        final UserRecommendation useCaseUserRecommendation = userRecommendationRepository.findFirstByUseCase(useCase);
        if (useCaseUserRecommendation != null) {
            referencedWarning.setKey("useCase.userRecommendation.useCase.referenced");
            referencedWarning.addParam(useCaseUserRecommendation.getRecommendationId());
            return referencedWarning;
        }
        return null;
    }

}
