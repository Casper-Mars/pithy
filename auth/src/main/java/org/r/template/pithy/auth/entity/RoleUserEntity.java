package org.r.template.pithy.auth.entity;

import org.r.template.pithy.commom.entity.BaseEntity;

/**
 * date 2020/6/2 下午4:19
 *
 * @author casper
 **/
public class RoleUserEntity extends BaseEntity<RoleUserEntity> {


    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
