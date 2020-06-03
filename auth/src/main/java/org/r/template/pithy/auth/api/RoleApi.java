package org.r.template.pithy.auth.api;

import org.r.template.pithy.auth.entity.PermissionEntity;
import org.r.template.pithy.auth.entity.RoleEntity;
import org.r.template.pithy.auth.common.PermissionBo;
import org.r.template.pithy.auth.pojo.dto.RoleDto;
import org.r.template.pithy.auth.service.PermissionService;
import org.r.template.pithy.auth.service.RoleService;
import org.r.template.pithy.commom.enums.ResultCodeEnum;
import org.r.template.pithy.commom.pojo.PageBo;
import org.r.template.pithy.commom.rpc.DeleteDto;
import org.r.template.pithy.commom.rpc.PageDto;
import org.r.template.pithy.commom.rpc.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 * date 2020/6/3 上午10:32
 *
 * @author casper
 **/
@RestController
@RequestMapping("/api/role")
public class RoleApi {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 添加角色
     *
     * @param dto 角色信息
     * @return
     */
    @PostMapping("")
    public ResultDto<Long> add(@RequestBody RoleDto dto) {
        /*校验参数*/
        if (StringUtils.isEmpty(dto.getName())) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), "角色名不能为空");
        }

        RoleEntity entity = new RoleEntity();
        entity.setRoleName(dto.getName());
        /*判断是否存在*/
        if (roleService.isExist(entity)) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), String.format("角色%s已经存在", dto.getName()));
        }

        Long roleId = roleService.saveRole(entity);
        return ResultDto.success(roleId);
    }

    /**
     * 更新角色信息
     *
     * @param dto 角色信息
     * @return
     */
    @PutMapping("")
    public ResultDto<Long> update(@RequestBody RoleDto dto) {
        /*校验参数*/
        if (StringUtils.isEmpty(dto.getName())) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), "角色名不能为空");
        }
        /*根据id查询角色*/
        RoleEntity role = roleService.getRoleById(dto.getId());
        if (role == null) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), String.format("角色%s不存在", dto.getName()));
        }
        role.setRoleName(dto.getName());
        if (roleService.isExist(role)) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), String.format("角色%s已经存在", dto.getName()));
        }
        roleService.updateRole(role);
        return ResultDto.success(role.getId());
    }

    /**
     * 查询角色列表
     *
     * @param roleName 角色名称
     * @param pageNo   页号
     * @param pageSize 页大小
     * @return
     */
    @GetMapping("/list")
    public PageDto<List<RoleDto>> list(
            String roleName,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        RoleEntity entity = new RoleEntity();
        entity.setRoleName(roleName);
        PageBo<RoleEntity> rolePageBo = roleService.listPage(entity, pageNo, pageSize);
        List<RoleDto> roleDtos = RoleDto.buildList(rolePageBo.getData());
        return PageDto.success(roleDtos, rolePageBo.getTotalPage(), rolePageBo.getTotalSize());
    }

    /**
     * 批量删除
     *
     * @param ids 待删除的id
     * @return
     */
    @DeleteMapping()
    public ResultDto<String> delete(@RequestBody DeleteDto<Long> ids) {
        roleService.deleteRolesByIds(ids.getIds());
        return ResultDto.success("success");
    }


    /**
     * 根据角色id获取角色的权限列表
     *
     * @param roleId 角色id
     * @return
     */
    @GetMapping("/permission/list")
    public ResultDto<List<PermissionBo>> permissions(@RequestParam Long roleId) {
        List<PermissionBo> permissions = roleService.getPermissions(roleId);
        return ResultDto.success(permissions);
    }

    /**
     * 编辑角色的权限
     *
     * @param dto 角色权限信息
     * @return
     */
    @PostMapping("/permission")
    public ResultDto<String> addPermissions(@RequestBody RoleDto dto) {

        RoleEntity role = roleService.getRoleById(dto.getId());
        if (role == null) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), "角色不存在");
        }
        List<PermissionEntity> permissionEntities = permissionService.getByIds(dto.getPermissionIds());
        if (CollectionUtils.isEmpty(permissionEntities)) {
            return ResultDto.error(ResultCodeEnum.PARAMS_ERROR.getCode(), "权限不存在");
        }

        roleService.linkPermissionToRole(role, permissionEntities);
        return ResultDto.success("success");
    }


}
