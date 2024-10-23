package io.bootify.usecase_app.service;

import io.bootify.usecase_app.domain.Dimension;
import io.bootify.usecase_app.model.DimensionDTO;
import io.bootify.usecase_app.repos.DimensionRepository;
import io.bootify.usecase_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DimensionService {

    private final DimensionRepository dimensionRepository;

    public DimensionService(final DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    public List<DimensionDTO> findAll() {
        final List<Dimension> dimensions = dimensionRepository.findAll(Sort.by("dimensionId"));
        return dimensions.stream()
                .map(dimension -> mapToDTO(dimension, new DimensionDTO()))
                .toList();
    }

    public DimensionDTO get(final Long dimensionId) {
        return dimensionRepository.findById(dimensionId)
                .map(dimension -> mapToDTO(dimension, new DimensionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DimensionDTO dimensionDTO) {
        final Dimension dimension = new Dimension();
        mapToEntity(dimensionDTO, dimension);
        return dimensionRepository.save(dimension).getDimensionId();
    }

    public void update(final Long dimensionId, final DimensionDTO dimensionDTO) {
        final Dimension dimension = dimensionRepository.findById(dimensionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dimensionDTO, dimension);
        dimensionRepository.save(dimension);
    }

    public void delete(final Long dimensionId) {
        dimensionRepository.deleteById(dimensionId);
    }

    private DimensionDTO mapToDTO(final Dimension dimension, final DimensionDTO dimensionDTO) {
        dimensionDTO.setDimensionId(dimension.getDimensionId());
        dimensionDTO.setDimensionName(dimension.getDimensionName());
        return dimensionDTO;
    }

    private Dimension mapToEntity(final DimensionDTO dimensionDTO, final Dimension dimension) {
        dimension.setDimensionName(dimensionDTO.getDimensionName());
        return dimension;
    }

}
