package io.bootify.usecase_app.rest;

import io.bootify.usecase_app.model.RoleDTO;
import io.bootify.usecase_app.service.RoleService;
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
@RequestMapping(value = "/api/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleResource {

    private final RoleService roleService;

    public RoleResource(final RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable(name = "roleId") final Long roleId) {
        return ResponseEntity.ok(roleService.get(roleId));
    }

    @PostMapping
    public ResponseEntity<Long> createRole(@RequestBody @Valid final RoleDTO roleDTO) {
        final Long createdRoleId = roleService.create(roleDTO);
        return new ResponseEntity<>(createdRoleId, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Long> updateRole(@PathVariable(name = "roleId") final Long roleId,
            @RequestBody @Valid final RoleDTO roleDTO) {
        roleService.update(roleId, roleDTO);
        return ResponseEntity.ok(roleId);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable(name = "roleId") final Long roleId) {
        roleService.delete(roleId);
        return ResponseEntity.noContent().build();
    }

}
