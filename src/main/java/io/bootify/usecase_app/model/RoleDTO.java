package io.bootify.usecase_app.model;

import jakarta.validation.constraints.Size;


public class RoleDTO {

    private Long roleId;

    @Size(max = 255)
    private String roleName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

}
