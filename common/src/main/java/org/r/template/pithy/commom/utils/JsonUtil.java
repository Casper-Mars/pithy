package org.r.template.pithy.commom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * date 2020/6/2 下午1:31
 *
 * @author casper
 **/
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 对象转成成字符串
     *
     * @param target 对象
     * @return
     * @throws JsonProcessingException
     */
    public static String toJsonString(Object target) throws JsonProcessingException {
        return objectMapper.writeValueAsString(target);
    }

    /**
     * 反序列化字符串
     *
     * @param str   数据字符串
     * @param clazz 反序列化出来的类的类型
     * @param <T>   泛型形参
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T parseJsonStr(String str, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(str, clazz);
    }

    /**
     * 反序列化字符串成数组
     *
     * @param str      数据数组字符串
     * @param eleClass 反序列化出来的类的类型
     * @param <T>      泛型形参
     * @return
     * @throws JsonProcessingException
     */
    public static <T> List<T> parseJsonList(String str, Class<T> eleClass) throws JsonProcessingException {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, eleClass);
        return objectMapper.readValue(str, javaType);
    }


}
