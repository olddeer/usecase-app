package io.bootify.usecase_app.rest;

import io.bootify.usecase_app.model.UserRecommendationDTO;
import io.bootify.usecase_app.service.UserRecommendationService;
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
@RequestMapping(value = "/api/userRecommendations", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRecommendationResource {

    private final UserRecommendationService userRecommendationService;

    public UserRecommendationResource(final UserRecommendationService userRecommendationService) {
        this.userRecommendationService = userRecommendationService;
    }

    @GetMapping
    public ResponseEntity<List<UserRecommendationDTO>> getAllUserRecommendations() {
        return ResponseEntity.ok(userRecommendationService.findAll());
    }

    @GetMapping("/{recommendationId}")
    public ResponseEntity<UserRecommendationDTO> getUserRecommendation(
            @PathVariable(name = "recommendationId") final Long recommendationId) {
        return ResponseEntity.ok(userRecommendationService.get(recommendationId));
    }

    @PostMapping
    public ResponseEntity<Long> createUserRecommendation(
            @RequestBody @Valid final UserRecommendationDTO userRecommendationDTO) {
        final Long createdRecommendationId = userRecommendationService.create(userRecommendationDTO);
        return new ResponseEntity<>(createdRecommendationId, HttpStatus.CREATED);
    }

    @PutMapping("/{recommendationId}")
    public ResponseEntity<Long> updateUserRecommendation(
            @PathVariable(name = "recommendationId") final Long recommendationId,
            @RequestBody @Valid final UserRecommendationDTO userRecommendationDTO) {
        userRecommendationService.update(recommendationId, userRecommendationDTO);
        return ResponseEntity.ok(recommendationId);
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Void> deleteUserRecommendation(
            @PathVariable(name = "recommendationId") final Long recommendationId) {
        userRecommendationService.delete(recommendationId);
        return ResponseEntity.noContent().build();
    }

}
