package org.r.template.pithy.auth;

import org.r.template.pithy.auth.aop.TokenAop;
import org.r.template.pithy.auth.config.JwtConfigBean;
import org.r.template.pithy.auth.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * date 2020/6/2 上午9:29
 *
 * @author casper
 **/
@EnableConfigurationProperties(JwtConfigBean.class)
public class AuthCommonAutoConfig {

    @Autowired
    private JwtConfigBean commonConfigBean;

    /**
     * 注入jwt服务
     *
     * @return
     */
    @Bean
    public JwtService jwtService() {
        String key = commonConfigBean.getKey();
        key = key == null ? "" : key;
        return new JwtService(key, commonConfigBean.getTtl());
    }





}
