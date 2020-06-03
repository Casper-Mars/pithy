package org.r.template.pithy.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.r.template.pithy.auth.entity.RoleUserEntity;

import java.util.List;

/**
 * @author casper
 * @date 2020/6/2 下午4:18
 **/
public interface RoleUserDao extends BaseMapper<RoleUserEntity> {

    /**
     * 通过用户id查询用户所有的角色
     *
     * @param userId 用户id
     * @return
     */
    List<RoleUserEntity> findByUserId(Long userId);


}
