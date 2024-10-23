package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UseCaseRepository extends JpaRepository<UseCase, Long> {

    UseCase findFirstByUser(User user);

}
