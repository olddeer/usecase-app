package io.bootify.usecase_app.rest;


import io.bootify.usecase_app.domain.UserAction;
import io.bootify.usecase_app.model.UserActionDTO;
import io.bootify.usecase_app.service.UserActionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-actions")
@AllArgsConstructor
public class UserActionResource {

    private final UserActionService userActionService;

    @GetMapping
    public List<UserAction> getAllUserActions() {
        return userActionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAction> getUserActionById(@PathVariable Long id) {
        Optional<UserAction> userAction = userActionService.findById(id);
        return userAction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserAction createUserAction(@RequestBody UserActionDTO userActionDTO) {
        return userActionService.save(userActionDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAction> updateUserAction(@PathVariable Long id, @RequestBody UserActionDTO userActionDTO) {
        if (userActionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userActionDTO.setActionId(id);
        UserAction updatedUserAction = userActionService.save(userActionDTO);
        return ResponseEntity.ok(updatedUserAction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAction(@PathVariable Long id) {
        if (userActionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userActionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
