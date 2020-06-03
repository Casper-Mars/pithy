package org.r.template.pithy.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.r.template.pithy.commom.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/11.
 */
public class JwtService {

    private final Logger log = LoggerFactory.getLogger(JwtService.class);

    /**
     * 加密的盐
     */
    private final String key;

    /**
     * 有效时间
     */
    private final long ttl;

    public JwtService(String key, long ttl) {
        this.key = key;
        this.ttl = ttl;
    }


    /**
     * 生成JWT
     *
     * @param id       唯一标识符
     * @param subject  用户对象
     * @param metaData 其他附属数据
     * @return
     */
    public String createJWT(String id, Object subject, Map<String, Object> metaData) throws JsonProcessingException {
        String subjectStr = "";
        if (subject != null) {
            subjectStr = JsonUtil.toJsonString(subject);
        }
        return createJWT(id, subjectStr, metaData);
    }


    /**
     * 生成JWT
     *
     * @param id       唯一标识符
     * @param subject  用户对象
     * @param metaData 其他附属数据
     * @return
     */
    public String createJWT(String id, String subject, Map<String, Object> metaData) {
        if (id == null || "".equals(id)) {
            throw new RuntimeException("id can not be null");
        }
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);

        if (metaData != null && !metaData.isEmpty()) {
            for (Map.Entry<String, Object> entry : metaData.entrySet()) {
                builder.claim(entry.getKey(), entry.getValue());
            }
        }
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * 解析JWT
     *
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtStr)
                    .getBody();
        } catch (Exception e) {
            log.error("解析jwt有误");
        }
        return body;
    }

}
