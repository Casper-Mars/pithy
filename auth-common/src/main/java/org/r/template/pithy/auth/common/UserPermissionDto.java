package org.r.template.pithy.auth.common;

import java.util.List;

/**
 * date 2020/6/3 下午4:44
 *
 * @author casper
 **/
public class UserPermissionDto {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限列表
     */
    private List<PermissionBo> permissionBos;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<PermissionBo> getPermissionBos() {
        return permissionBos;
    }

    public void setPermissionBos(List<PermissionBo> permissionBos) {
        this.permissionBos = permissionBos;
    }
}
