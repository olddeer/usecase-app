package io.bootify.usecase_app.service;

import io.bootify.usecase_app.domain.Role;
import io.bootify.usecase_app.domain.UseCase;
import io.bootify.usecase_app.domain.User;
import io.bootify.usecase_app.model.UserDTO;
import io.bootify.usecase_app.repos.RoleRepository;
import io.bootify.usecase_app.repos.UseCaseRepository;
import io.bootify.usecase_app.repos.UserRepository;
import io.bootify.usecase_app.util.NotFoundException;
import io.bootify.usecase_app.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UseCaseRepository useCaseRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
            final UseCaseRepository useCaseRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.useCaseRepository = useCaseRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoles(user.getRoles().stream()
                .map(role -> role.getRoleId())
                .toList());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        final List<Role> roles = roleRepository.findAllById(
                userDTO.getRoles() == null ? Collections.emptyList() : userDTO.getRoles());
        if (roles.size() != (userDTO.getRoles() == null ? 0 : userDTO.getRoles().size())) {
            throw new NotFoundException("one of roles not found");
        }
        user.setRoles(new HashSet<>(roles));
        return user;
    }

    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final UseCase userUseCase = useCaseRepository.findFirstByUser(user);
        if (userUseCase != null) {
            referencedWarning.setKey("user.useCase.user.referenced");
            referencedWarning.addParam(userUseCase.getUseCaseId());
            return referencedWarning;
        }
        return null;
    }

}
