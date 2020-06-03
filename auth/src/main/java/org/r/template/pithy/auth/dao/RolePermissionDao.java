package org.r.template.pithy.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.r.template.pithy.auth.entity.RolePermissionEntity;

import java.util.List;

/**
 * @author casper
 * @date 2020/6/2 下午4:50
 **/
public interface RolePermissionDao extends BaseMapper<RolePermissionEntity> {

    /**
     * 根据角色id查询有关的权限信息
     *
     * @param roleId 角色id
     * @return
     */
    List<RolePermissionEntity> findByRoleId(Long roleId);


    /**
     * 根据角色id查询有关的权限信息
     *
     * @param roleIds 角色id集合
     * @return
     */
    List<RolePermissionEntity> findByRoleIds(List<Long> roleIds);


}
