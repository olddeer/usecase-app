package io.bootify.usecase_app.repos;

import io.bootify.usecase_app.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
