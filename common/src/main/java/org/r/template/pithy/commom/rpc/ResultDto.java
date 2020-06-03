package org.r.template.pithy.commom.rpc;

import org.r.template.pithy.commom.enums.ResultCodeEnum;

/**
 * date 2020/6/2 下午2:22
 *
 * @author casper
 **/
public class ResultDto<T> {

    /**
     * 代码
     */
    private int code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public ResultDto() {
    }

    public ResultDto(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> ResultDto<T> success(T data) {
        return new ResultDto<>(200, "success", data);
    }

    public static <T> ResultDto<T> error(int code, String msg) {
        return error(code, msg, null);
    }

    public static <T> ResultDto<T> error(int code, String msg, T data) {
        return new ResultDto<>(code, msg, data);
    }

    public static <T> ResultDto<T> error(ResultCodeEnum resultCodeEnum) {
        return error(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

}
