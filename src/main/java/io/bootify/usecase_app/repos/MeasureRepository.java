package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.Measure;
import io.bootify.usecase_app.domain.UseCase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MeasureRepository extends JpaRepository<Measure, Long> {

    Measure findFirstByUseCases(UseCase useCase);

    List<Measure> findAllByUseCases(UseCase useCase);

}
