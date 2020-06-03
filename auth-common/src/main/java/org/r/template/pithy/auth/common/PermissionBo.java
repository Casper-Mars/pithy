package org.r.template.pithy.auth.common;

import java.util.LinkedList;
import java.util.List;

/**
 * date 2020/6/2 下午4:24
 *
 * @author casper
 **/
public class PermissionBo {

    /**
     * 权限id
     */
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限路径
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

    private List<PermissionBo> children = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<PermissionBo> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionBo> children) {
        this.children = children;
    }

    public void addChild(PermissionBo child) {
        this.children.add(child);
    }

    public void addChildren(List<PermissionBo> children) {
        this.children.addAll(children);
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
