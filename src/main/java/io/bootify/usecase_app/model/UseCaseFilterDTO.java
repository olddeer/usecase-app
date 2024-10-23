package io.bootify.usecase_app.model;


public record UseCaseFilterDTO (Long useCaseFilterId, String filterOperation, String filterValue, DimensionDTO dimension) {
}
