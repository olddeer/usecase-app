package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.Comment;
import io.bootify.usecase_app.domain.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findFirstByUseCase(UseCase useCase);

}
