package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.UseCaseFilter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UseCaseFilterRepository extends JpaRepository<UseCaseFilter, Long> {
}
