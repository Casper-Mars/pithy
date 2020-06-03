package org.r.template.pithy.commom.pojo;

import java.util.List;

/**
 * date 2020/6/3 下午3:49
 *
 * @author casper
 **/
public class PageBo<T> {

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 总数
     */
    private long totalSize;

    /**
     * 角色信息
     */
    private List<T> data;

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
