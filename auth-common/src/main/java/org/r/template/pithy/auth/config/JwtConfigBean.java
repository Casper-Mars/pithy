package org.r.template.pithy.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * date 2020/6/2 上午9:38
 *
 * @author casper
 **/
@ConfigurationProperties(prefix = "pithy.auth")
@Component
public class JwtConfigBean {

    /**
     * 加密的盐
     */
    private String key;

    /**
     * 有效时间
     */
    private long ttl;

    /**
     * aop配置
     */
    @NestedConfigurationProperty
    private AuthAopConfigBean aop;

    public AuthAopConfigBean getAop() {
        return aop;
    }

    public void setAop(AuthAopConfigBean aop) {
        this.aop = aop;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
