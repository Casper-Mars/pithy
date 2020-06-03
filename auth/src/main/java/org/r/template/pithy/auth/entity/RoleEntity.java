package org.r.template.pithy.auth.entity;

import org.r.template.pithy.commom.entity.BaseEntity;

/**
 * date 2020/6/2 下午4:18
 *
 * @author casper
 **/
public class RoleEntity extends BaseEntity<RoleEntity> {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否启用
     */
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
