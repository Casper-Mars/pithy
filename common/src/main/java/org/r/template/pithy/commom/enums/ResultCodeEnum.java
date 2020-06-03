package org.r.template.pithy.commom.enums;

/**
 * date 2020/6/3 上午10:40
 *
 * @author casper
 **/
public enum ResultCodeEnum {

    SUCCESS(200, "success"),
    PARAMS_ERROR(401, "参数校验出错"),
    INTERNAL_ERROR(500, "内部错误"),
    AUTH_NOT_PERMIT(400, "没有权限"),


    ;

    private int code;

    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
