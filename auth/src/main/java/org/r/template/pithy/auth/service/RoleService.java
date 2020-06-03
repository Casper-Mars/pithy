package org.r.template.pithy.auth.service;

import org.r.template.pithy.auth.entity.PermissionEntity;
import org.r.template.pithy.auth.entity.RoleEntity;
import org.r.template.pithy.auth.exception.PermissionTreeException;
import org.r.template.pithy.auth.common.PermissionBo;
import org.r.template.pithy.commom.pojo.PageBo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * date 2020/6/2 下午4:18
 *
 * @author casper
 **/
public interface RoleService {


    /**
     * 查询指定角色的权限列表
     *
     * @param roleId 角色id
     * @return
     */
    List<PermissionBo> getPermissions(Long roleId);

    /**
     * 从持久层中查询指定角色的权限
     *
     * @param roleId 角色id
     * @return
     */
    List<PermissionBo> getPermissionsFromDatabase(Long roleId);

    /**
     * 添加角色
     *
     * @param entity 角色信息
     * @return
     */
    @Transactional
    Long saveRole(RoleEntity entity);

    /**
     * 判断角色是否存在
     *
     * @param entity 角色信息
     * @return
     */
    boolean isExist(RoleEntity entity);

    /**
     * 根据id查询角色信息
     *
     * @param id 角色id
     * @return
     */
    RoleEntity getRoleById(Long id);

    /**
     * 更新角色信息
     *
     * @param entity 角色信息
     * @return
     */
    @Transactional
    Long updateRole(RoleEntity entity);


    /**
     * 分页查询角色信息
     *
     * @param entity   角色查询信息
     * @param pageNo   页号
     * @param pageSize 页大小
     * @return
     */
    PageBo<RoleEntity> listPage(RoleEntity entity, int pageNo, int pageSize);

    /**
     * 绑定权限到指定的角色
     *
     * @param role        角色id
     * @param permissions 权限id数组
     * @throws PermissionTreeException 如果绑定的权限不能构造出一个完整的权限树，则抛出此异常
     */
    void linkPermissionToRole(RoleEntity role, List<PermissionEntity> permissions);

    /**
     * 批量删除角色
     *
     * @param roleIds 角色id集合
     */
    void deleteRolesByIds(List<Long> roleIds);

    /**
     * 根据id集合查询角色信息
     *
     * @param roleIds 角色id集合
     * @return
     */
    List<RoleEntity> getRoleByIds(List<Long> roleIds);


    /**
     * 获取指定用户的角色列表
     *
     * @param userId 用户id
     * @return
     */
    List<RoleEntity> getRolesByUserId(Long userId);


}
