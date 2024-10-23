package io.bootify.usecase_app.model;

import io.bootify.usecase_app.domain.UseCaseFilter;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UseCaseDTO {

    private Long useCaseId;
    private LocalDateTime publishedDateTime;
    private LocalDateTime lastUpdatedDateTime;
    private String userName;
    private List<DimensionDTO> dimensions;
    private List<MeasureDTO> measures;
    private List<UseCaseFilterDTO> useCaseFilters;
    private List<CommentDTO> comments;

}
