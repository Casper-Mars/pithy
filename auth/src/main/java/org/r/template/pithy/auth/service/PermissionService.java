package org.r.template.pithy.auth.service;

import org.r.template.pithy.auth.entity.PermissionEntity;

import java.util.List;

/**
 * date 2020/6/3 上午10:57
 *
 * @author casper
 **/
public interface PermissionService {

    /**
     * 保存权限
     *
     * @param permission 权限
     * @return
     */
    Long savePermission(PermissionEntity permission);


    /**
     * 查询指定id集合的权限
     *
     * @param permissionIds 权限id集合
     * @return
     */
    List<PermissionEntity> getByIds(List<Long> permissionIds);


}
