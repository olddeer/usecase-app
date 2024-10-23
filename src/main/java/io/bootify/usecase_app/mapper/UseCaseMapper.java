package io.bootify.usecase_app.mapper;

import io.bootify.usecase_app.domain.*;
import io.bootify.usecase_app.model.CommentDTO;
import io.bootify.usecase_app.model.DimensionDTO;
import io.bootify.usecase_app.model.MeasureDTO;
import io.bootify.usecase_app.model.UseCaseFilterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UseCaseMapper {

    List<CommentDTO> toCommentDTOs(Set<Comment> comments);

    CommentDTO toCommentDTO(Comment comment);

    List<DimensionDTO> toDimensionDTOs(Set<Dimension> dimensions);


    DimensionDTO toDimensionDTO(Dimension dimension);
    List<MeasureDTO> toMeasureDTOs(Set<Measure> measures);

    MeasureDTO toMeasureDTO(Measure measure);
    List<UseCaseFilterDTO> toUseCaseFilterDTOs(Set<UseCaseFilter> filters);
}
