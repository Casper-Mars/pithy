package org.r.template.pithy.auth.entity;

import org.r.template.pithy.commom.entity.BaseEntity;

/**
 * date 2020/6/2 下午4:51
 *
 * @author casper
 **/
public class RolePermissionEntity extends BaseEntity<RolePermissionEntity> {


    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
