package org.r.template.pithy.commom.pojo;

import java.util.List;

/**
 * date 2020/6/2 下午4:29
 *
 * @author casper
 **/
public interface InnerBuilder<S, T> {

    /**
     * 从源bean封装成目标bean
     *
     * @param source 源
     * @return
     */
    T build(S source);

    /**
     * 批量从源bean封装成目标bean
     *
     * @param sources 源
     * @return
     */
    List<T> build(List<S> sources);

}
