package org.r.template.pithy.commom.rpc;

import org.r.template.pithy.commom.enums.ResultCodeEnum;

/**
 * date 2020/6/3 上午11:18
 *
 * @author casper
 **/
public class PageDto<T> extends ResultDto<T> {


    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 总数
     */
    private Long totalSize;


    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public PageDto() {
    }

    public PageDto(int code, String msg, T data, Long totalPage, Long totalSize) {
        super(code, msg, data);
        this.totalPage = totalPage;
        this.totalSize = totalSize;
    }

    public static <T> PageDto<T> success(T list, long totalPage, long totalSize) {
        return new PageDto<T>(
                ResultCodeEnum.SUCCESS.getCode(),
                ResultCodeEnum.SUCCESS.getMsg(),
                list,
                totalPage,
                totalSize
        );
    }






}
