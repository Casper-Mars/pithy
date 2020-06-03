package org.r.template.pithy.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * date 2020/6/2 下午5:55
 *
 * @author casper
 **/
@Component
@ConfigurationProperties(prefix = "pithy.auth")
public class AuthConfigBean {

    /**
     * 权限树缓存的时间
     */
    private long permissionCacheTime;

    public long getPermissionCacheTime() {
        return permissionCacheTime;
    }

    public void setPermissionCacheTime(long permissionCacheTime) {
        this.permissionCacheTime = permissionCacheTime;
    }
}
