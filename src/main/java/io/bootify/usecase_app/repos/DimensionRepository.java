package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.Dimension;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DimensionRepository extends JpaRepository<Dimension, Long> {
}
