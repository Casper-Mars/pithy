package org.r.template.pithy.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.r.template.pithy.auth.config.AuthConfigBean;
import org.r.template.pithy.auth.dao.PermissionDao;
import org.r.template.pithy.auth.dao.RoleDao;
import org.r.template.pithy.auth.dao.RolePermissionDao;
import org.r.template.pithy.auth.dao.RoleUserDao;
import org.r.template.pithy.auth.entity.PermissionEntity;
import org.r.template.pithy.auth.entity.RoleEntity;
import org.r.template.pithy.auth.entity.RolePermissionEntity;
import org.r.template.pithy.auth.entity.RoleUserEntity;
import org.r.template.pithy.auth.exception.PermissionTreeException;
import org.r.template.pithy.auth.common.PermissionBo;
import org.r.template.pithy.auth.pojo.bo.PermissionBoBuilder;
import org.r.template.pithy.auth.service.RoleService;
import org.r.template.pithy.cache.common.service.CacheService;
import org.r.template.pithy.commom.pojo.PageBo;
import org.r.template.pithy.commom.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * date 2020/6/3 上午10:58
 *
 * @author casper
 **/
public class RoleServiceImpl implements RoleService {
    private final static Logger log = LoggerFactory.getLogger(RoleService.class);

    private final static String PERMISSION_TREE_CACHE_KEY = "permission_tree_cache_key_";

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired(required = false)
    private CacheService cacheService;

    @Autowired
    private AuthConfigBean authConfigBean;


    /**
     * 查询指定角色的权限列表
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<PermissionBo> getPermissions(Long roleId) {
        String key = PERMISSION_TREE_CACHE_KEY + roleId;
        List<PermissionBo> result = null;
        boolean needSearchFromDatabase = true;
        if (cacheService != null) {
            String str = cacheService.getStr(key);
            if (!StringUtils.isEmpty(str)) {
                try {
                    result = JsonUtil.parseJsonList(str, PermissionBo.class);
                    needSearchFromDatabase = false;
                } catch (JsonProcessingException e) {
                    log.error("从缓存中获取角色的权限列表时，反序列化json出错！！！！！，msg:{}", e.getMessage());
                }
            }
        }
        if (needSearchFromDatabase) {
            result = getPermissionsFromDatabase(roleId);
            if (cacheService != null) {
                try {
                    String target = JsonUtil.toJsonString(result);
                    if (authConfigBean.getPermissionCacheTime() > 0) {
                        cacheService.putStr(key, target, authConfigBean.getPermissionCacheTime());
                    } else {
                        cacheService.putStr(key, target);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return result == null ? Collections.emptyList() : result;
    }

    /**
     * 从持久层中查询指定角色的权限
     *
     * @param roleId 角色id
     * @return
     */
    @Override
    public List<PermissionBo> getPermissionsFromDatabase(Long roleId) {

        List<RolePermissionEntity> rolePermissions = rolePermissionDao.findByRoleId(roleId);
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = rolePermissions.stream().map(RolePermissionEntity::getPermissionId).collect(Collectors.toList());
        List<PermissionEntity> permissions = permissionDao.findInIds(permissionIds);
        if (CollectionUtils.isEmpty(permissionIds)) {
            return Collections.emptyList();
        }
        return PermissionBoBuilder.build(permissions);
    }

    /**
     * 添加角色
     *
     * @param entity 角色信息
     * @return
     */
    @Transactional
    @Override
    public Long saveRole(RoleEntity entity) {
        roleDao.insert(entity);
        return entity.getId();
    }

    /**
     * 判断角色是否存在
     *
     * @param entity 角色信息
     * @return
     */
    @Override
    public boolean isExist(RoleEntity entity) {
        if (StringUtils.isEmpty(entity.getRoleName())) {
            return false;
        }
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RoleEntity::getRoleName, entity.getRoleName());
        if (entity.getId() != null && entity.getId() != 0) {
            queryWrapper.lambda().ne(RoleEntity::getId, entity.getId());
        }
        return roleDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 根据id查询角色信息
     *
     * @param id 角色id
     * @return
     */
    @Override
    public RoleEntity getRoleById(Long id) {
        return roleDao.selectById(id);
    }

    /**
     * 更新角色信息
     *
     * @param entity 角色信息
     * @return
     */
    @Transactional
    @Override
    public Long updateRole(RoleEntity entity) {
        roleDao.updateById(entity);
        return entity.getId();
    }

    /**
     * 分页查询角色信息
     *
     * @param entity   角色查询信息
     * @param pageNo   页号
     * @param pageSize 页大小
     * @return
     */
    @Override
    public PageBo<RoleEntity> listPage(RoleEntity entity, int pageNo, int pageSize) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(entity.getRoleName())) {
            queryWrapper.lambda().like(RoleEntity::getRoleName, entity.getRoleName());
        }
        IPage<RoleEntity> page = new Page<>();
        page.setCurrent(pageNo);
        page.setSize(pageSize);
        IPage<RoleEntity> entityPage = roleDao.selectPage(page, queryWrapper);
        PageBo<RoleEntity> pageBo = new PageBo<>();
        pageBo.setTotalPage(entityPage.getPages());
        pageBo.setTotalSize(entityPage.getTotal());
        pageBo.setData(entityPage.getRecords());
        return pageBo;
    }

    /**
     * 绑定权限到指定的角色
     * 采用清除重做的策略。先清除角色的权限，再重新写入
     *
     * @param role        角色id
     * @param permissions 权限id数组
     * @throws PermissionTreeException 如果绑定的权限不能构造出一个完整的权限树，则抛出此异常
     */
    @Override
    public void linkPermissionToRole(RoleEntity role, List<PermissionEntity> permissions) {
        if (role == null || CollectionUtils.isEmpty(permissions)) {
            return;
        }
        /*删除旧的权限*/
        rolePermissionDao.delete(new QueryWrapper<RolePermissionEntity>().lambda().eq(RolePermissionEntity::getRoleId, role.getId()));

        /*删除缓存的权限*/
        if (cacheService != null) {
            cacheService.delStr(PERMISSION_TREE_CACHE_KEY + role.getId());
        }
        /*检查权限能否构建出权限树*/
        /*生成父id集合，利用hash的特性，便于检索*/
        Set<Long> parentIds = permissions.stream().map(PermissionEntity::getId).collect(Collectors.toSet());
        /*列表检查权限，如果父id不在父id集合中的，就不能满足构建树的条件*/
        for (PermissionEntity permission : permissions) {
            if (permission.getParentId() != null) {
                if (!parentIds.contains(permission.getParentId())) {
                    throw new PermissionTreeException("权限不完整，出现断层");
                }
            }
        }
        /*保存角色权限*/
        for (PermissionEntity permission : permissions) {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setPermissionId(permission.getId());
            rolePermissionEntity.setRoleId(role.getId());
            rolePermissionDao.insert(rolePermissionEntity);
        }

        /*删除缓存的权限*/
        if (cacheService != null) {
            cacheService.delStr(PERMISSION_TREE_CACHE_KEY + role.getId());
        }
    }

    /**
     * 批量删除角色
     *
     * @param roleIds 角色id集合
     */
    @Override
    @Transactional
    public void deleteRolesByIds(List<Long> roleIds) {

        /*
         * 需要删除
         * 1 角色的信息
         * 2 角色关联权限的信息
         * 3 用户关联角色的信息
         * 4 缓存的角色权限树
         * */

        /*查询角色信息*/
        List<RoleEntity> roles = getRoleByIds(roleIds);
        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }

        /*删除角色权限信息*/
        rolePermissionDao.delete(new QueryWrapper<RolePermissionEntity>().lambda().in(RolePermissionEntity::getRoleId, roleIds));
        /*删除角色用户信息*/
        roleUserDao.delete(new QueryWrapper<RoleUserEntity>().lambda().in(RoleUserEntity::getRoleId, roleIds));
        /*删除角色信息*/
        roleDao.deleteBatchIds(roleIds);
        /*清除缓存*/
        if (cacheService != null) {
            for (RoleEntity role : roles) {
                cacheService.delStr(PERMISSION_TREE_CACHE_KEY + role.getId());
            }
        }
    }

    /**
     * 根据id集合查询角色信息
     *
     * @param roleIds 角色id集合
     * @return
     */
    @Override
    public List<RoleEntity> getRoleByIds(List<Long> roleIds) {
        return roleDao.selectBatchIds(roleIds);
    }

    /**
     * 获取指定用户的角色列表
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<RoleEntity> getRolesByUserId(Long userId) {
        List<RoleUserEntity> roleUserEntities = roleUserDao.selectList(new QueryWrapper<RoleUserEntity>().lambda().eq(RoleUserEntity::getUserId, userId));
        if (CollectionUtils.isEmpty(roleUserEntities)) {
            return Collections.emptyList();
        }
        List<Long> roleIds = roleUserEntities.stream().map(RoleUserEntity::getRoleId).collect(Collectors.toList());
        return getRoleByIds(roleIds);
    }


}
