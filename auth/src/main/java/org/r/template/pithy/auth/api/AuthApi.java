package org.r.template.pithy.auth.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import org.r.template.pithy.auth.annotation.Token;
import org.r.template.pithy.auth.common.PermissionBo;
import org.r.template.pithy.auth.common.UserInfoBo;
import org.r.template.pithy.auth.common.UserPermissionDto;
import org.r.template.pithy.auth.entity.RoleEntity;
import org.r.template.pithy.auth.service.JwtService;
import org.r.template.pithy.auth.service.RoleService;
import org.r.template.pithy.commom.enums.ResultCodeEnum;
import org.r.template.pithy.commom.rpc.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * date 2020/6/2 下午2:21
 *
 * @author casper
 **/
@RestController
@RequestMapping("/api/auth")
public class AuthApi {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleService roleService;


    /**
     * 鉴权，生成jwt令牌
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/token")
    public ResultDto<String> token(String username, String password) {

        UserInfoBo userInfo = new UserInfoBo();
        String token = "";
        try {
            token = jwtService.createJWT(userInfo.getId().toString(), userInfo, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(token)) {
            return ResultDto.error(ResultCodeEnum.INTERNAL_ERROR.getCode(), "无法正常生成token");
        }
        return ResultDto.success(token);
    }


    /**
     * 查询用户的权限
     *
     * @param token 用户的令牌
     * @return
     */
    @GetMapping("/permissions")
    public ResultDto<List<UserPermissionDto>> permissions(@Token Claims token) {

        /*判断token*/
        long userId = 0L;
        try {
            userId = Long.parseLong(token.getId());
        } catch (Exception e) {
            return ResultDto.error(ResultCodeEnum.AUTH_NOT_PERMIT.getCode(), "令牌数据不正确");
        }
        List<RoleEntity> roles = roleService.getRolesByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return ResultDto.success(Collections.emptyList());
        }
        List<UserPermissionDto> result = new ArrayList<>(roles.size());
        for (RoleEntity role : roles) {
            List<PermissionBo> permissions = roleService.getPermissions(role.getId());
            UserPermissionDto dto = new UserPermissionDto();
            dto.setRoleName(role.getRoleName());
            dto.setPermissionBos(permissions);
            result.add(dto);
        }
        return ResultDto.success(result);
    }


}
