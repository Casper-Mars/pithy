package org.r.template.pithy.auth.entity;

import org.r.template.pithy.commom.entity.BaseEntity;

/**
 * date 2020/6/2 下午4:24
 *
 * @author casper
 **/
public class PermissionEntity extends BaseEntity<PermissionEntity> {

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限地址
     */
    private String url;

    /**
     * 权限标识符
     */
    private String identifier;

    /**
     * 父权限id
     */
    private Long parentId;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
