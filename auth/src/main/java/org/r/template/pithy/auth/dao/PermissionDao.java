package org.r.template.pithy.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.r.template.pithy.auth.entity.PermissionEntity;

import java.util.List;

/**
 * @author casper
 * @date 2020/6/2 下午4:50
 **/
public interface PermissionDao extends BaseMapper<PermissionEntity> {


    /**
     * 批量查询指定的id的权限记录
     *
     * @param ids 权限id集合
     * @return
     */
    List<PermissionEntity> findInIds(List<Long> ids);


}
