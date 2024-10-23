package io.bootify.usecase_app.service;

import io.bootify.usecase_app.domain.Comment;
import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.model.CommentDTO;
import io.bootify.usecase_app.repos.CommentRepository;
import io.bootify.usecase_app.repos.UseCaseRepository;
import io.bootify.usecase_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UseCaseRepository useCaseRepository;

    public CommentService(final CommentRepository commentRepository,
            final UseCaseRepository useCaseRepository) {
        this.commentRepository = commentRepository;
        this.useCaseRepository = useCaseRepository;
    }

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("commentId"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    }

    public CommentDTO get(final Long commentId) {
        return commentRepository.findById(commentId)
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CommentDTO commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);
        return commentRepository.save(comment).getCommentId();
    }

    public void update(final Long commentId, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(commentDTO, comment);
        commentRepository.save(comment);
    }

    public void delete(final Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setCommentId(comment.getCommentId());
        commentDTO.setComment(comment.getComment());
        commentDTO.setUseCaseId(comment.getUseCase() == null ? null : comment.getUseCase().getUseCaseId());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentDTO commentDTO, final Comment comment) {
        comment.setComment(commentDTO.getComment());
        final UseCase useCase = commentDTO.getUseCaseId() == null ? null : useCaseRepository.findById(commentDTO.getUseCaseId())
                .orElseThrow(() -> new NotFoundException("useCase not found"));
        comment.setUseCase(useCase);
        return comment;
    }

}
