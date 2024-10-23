package io.bootify.usecase_app.rest;

import io.bootify.usecase_app.model.DimensionDTO;
import io.bootify.usecase_app.service.DimensionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/dimensions", produces = MediaType.APPLICATION_JSON_VALUE)
public class DimensionResource {

    private final DimensionService dimensionService;

    public DimensionResource(final DimensionService dimensionService) {
        this.dimensionService = dimensionService;
    }

    @GetMapping
    public ResponseEntity<List<DimensionDTO>> getAllDimensions() {
        return ResponseEntity.ok(dimensionService.findAll());
    }

    @GetMapping("/{dimensionId}")
    public ResponseEntity<DimensionDTO> getDimension(
            @PathVariable(name = "dimensionId") final Long dimensionId) {
        return ResponseEntity.ok(dimensionService.get(dimensionId));
    }

    @PostMapping
    public ResponseEntity<Long> createDimension(
            @RequestBody @Valid final DimensionDTO dimensionDTO) {
        final Long createdDimensionId = dimensionService.create(dimensionDTO);
        return new ResponseEntity<>(createdDimensionId, HttpStatus.CREATED);
    }

    @PutMapping("/{dimensionId}")
    public ResponseEntity<Long> updateDimension(
            @PathVariable(name = "dimensionId") final Long dimensionId,
            @RequestBody @Valid final DimensionDTO dimensionDTO) {
        dimensionService.update(dimensionId, dimensionDTO);
        return ResponseEntity.ok(dimensionId);
    }

    @DeleteMapping("/{dimensionId}")
    public ResponseEntity<Void> deleteDimension(
            @PathVariable(name = "dimensionId") final Long dimensionId) {
        dimensionService.delete(dimensionId);
        return ResponseEntity.noContent().build();
    }

}
