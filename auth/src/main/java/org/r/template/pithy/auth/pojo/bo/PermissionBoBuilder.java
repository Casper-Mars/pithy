package org.r.template.pithy.auth.pojo.bo;

import org.r.template.pithy.auth.common.PermissionBo;
import org.r.template.pithy.auth.entity.PermissionEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * date 2020/6/3 下午4:50
 *
 * @author casper
 **/
public class PermissionBoBuilder {
    /**
     * 从源bean封装成目标bean
     *
     * @param permissionEntity 源
     * @return
     */
    public static PermissionBo build(PermissionEntity permissionEntity) {
        if (permissionEntity == null) {
            return null;
        }
        PermissionBo bo = new PermissionBo();
        bo.setId(permissionEntity.getId());
        bo.setIdentifier(permissionEntity.getIdentifier());
        bo.setName(permissionEntity.getPermissionName());
        bo.setUrl(permissionEntity.getUrl());
        return bo;
    }

    /**
     * 批量从源bean封装成目标bean
     *
     * @param permissionEntities 源
     * @return
     */
    public static List<PermissionBo> build(List<PermissionEntity> permissionEntities) {
        if (CollectionUtils.isEmpty(permissionEntities)) {
            return Collections.emptyList();
        }
        List<PermissionBo> results = new ArrayList<>(permissionEntities.size());
        for (PermissionEntity permissionEntity : permissionEntities) {
            PermissionBo build = build(permissionEntity);
            if (build == null) {
                continue;
            }
            results.add(build(permissionEntity));
        }
        return results;
    }
}
