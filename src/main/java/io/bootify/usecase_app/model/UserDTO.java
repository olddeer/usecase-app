package io.bootify.usecase_app.model;

import jakarta.validation.constraints.Size;
import java.util.List;


public class UserDTO {

    private Long id;

    @Size(max = 255)
    @UserUsernameUnique
    private String username;

    private List<Long> roles;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(final List<Long> roles) {
        this.roles = roles;
    }

}
