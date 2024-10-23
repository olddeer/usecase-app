package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.Role;
import io.bootify.usecase_app.domain.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByRoles(Role role);

    List<User> findAllByRoles(Role role);

    boolean existsByUsernameIgnoreCase(String username);

    Optional<User> findByUsername(String username);

}
