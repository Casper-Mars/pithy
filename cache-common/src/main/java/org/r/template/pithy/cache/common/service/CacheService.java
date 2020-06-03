package org.r.template.pithy.cache.common.service;

/**
 * date 2020/6/2 下午5:39
 *
 * @author casper
 **/
public interface CacheService {


    /**
     * 缓存字符型数据
     *
     * @param key    键
     * @param target 目标
     */
    void putStr(String key, String target);

    /**
     * 缓存字符型数据，并指定有效时间
     *
     * @param key        键
     * @param target     目标
     * @param millionSec 缓存有效时间
     */
    void putStr(String key, String target, long millionSec);

    /**
     * 获取缓存中的数据
     *
     * @param key 缓存的key
     * @return 字符型的数据
     */
    String getStr(String key);

    /**
     * 删除key
     *
     * @param key 缓存的key
     */
    void delStr(String key);

}
