package org.r.template.pithy.auth.pojo.dto;

import org.r.template.pithy.auth.entity.RoleEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * date 2020/6/3 上午10:33
 *
 * @author casper
 **/
public class RoleDto {

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 权限id
     */
    private List<Long> permissionIds;

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

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }


    /**
     * @param entity
     * @return
     */
    public static RoleDto build(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        RoleDto result = new RoleDto();
        result.setId(entity.getId());
        result.setName(entity.getRoleName());
        return result;
    }

    /**
     * 批量
     * @param entities
     * @return
     */
    public static List<RoleDto> buildList(List<RoleEntity> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }
        List<RoleDto> result = new ArrayList<>(entities.size());
        for (RoleEntity entity : entities) {
            result.add(build(entity));
        }
        return result;
    }


}
