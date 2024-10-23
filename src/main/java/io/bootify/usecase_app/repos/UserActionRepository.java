package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {
}
