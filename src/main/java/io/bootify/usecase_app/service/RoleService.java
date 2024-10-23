package io.bootify.usecase_app.service;

import io.bootify.usecase_app.domain.Role;
import io.bootify.usecase_app.model.RoleDTO;
import io.bootify.usecase_app.repos.RoleRepository;
import io.bootify.usecase_app.repos.UserRepository;
import io.bootify.usecase_app.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(final RoleRepository roleRepository, final UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("roleId"));
        return roles.stream()
                .map(role -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Long roleId) {
        return roleRepository.findById(roleId)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getRoleId();
    }

    public void update(final Long roleId, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(roleId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Long roleId) {
        final Role role = roleRepository.findById(roleId)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        userRepository.findAllByRoles(role)
                .forEach(user -> user.getRoles().remove(role));
        roleRepository.delete(role);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setRoleId(role.getRoleId());
        roleDTO.setRoleName(role.getRoleName());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setRoleName(roleDTO.getRoleName());
        return role;
    }

}
