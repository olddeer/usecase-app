package io.bootify.usecase_app.rest;

import io.bootify.usecase_app.model.UseCaseDTO;
import io.bootify.usecase_app.service.UseCaseService;
import io.bootify.usecase_app.util.ReferencedException;
import io.bootify.usecase_app.util.ReferencedWarning;
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
@RequestMapping(value = "/api/useCases", produces = MediaType.APPLICATION_JSON_VALUE)
public class UseCaseResource {

    private final UseCaseService useCaseService;

    public UseCaseResource(final UseCaseService useCaseService) {
        this.useCaseService = useCaseService;
    }

    @GetMapping
    public ResponseEntity<List<UseCaseDTO>> getAllUseCases() {
        return ResponseEntity.ok(useCaseService.findAll());
    }

    @GetMapping("/{useCaseId}")
    public ResponseEntity<UseCaseDTO> getUseCase(
            @PathVariable(name = "useCaseId") final Long useCaseId) {
        return ResponseEntity.ok(useCaseService.get(useCaseId));
    }

    @PostMapping
    public ResponseEntity<Long> createUseCase(@RequestBody @Valid final UseCaseDTO useCaseDTO) {
        final Long createdUseCaseId = useCaseService.create(useCaseDTO);
        return new ResponseEntity<>(createdUseCaseId, HttpStatus.CREATED);
    }

    @PutMapping("/{useCaseId}")
    public ResponseEntity<Long> updateUseCase(
            @PathVariable(name = "useCaseId") final Long useCaseId,
            @RequestBody @Valid final UseCaseDTO useCaseDTO) {
        useCaseService.update(useCaseId, useCaseDTO);
        return ResponseEntity.ok(useCaseId);
    }

    @DeleteMapping("/{useCaseId}")
    public ResponseEntity<Void> deleteUseCase(
            @PathVariable(name = "useCaseId") final Long useCaseId) {
        final ReferencedWarning referencedWarning = useCaseService.getReferencedWarning(useCaseId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        useCaseService.delete(useCaseId);
        return ResponseEntity.noContent().build();
    }

}
