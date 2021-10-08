package com.guet.coal_trading_platform.common.api;

/**
 * 枚举了一些常用API操作码
 *
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "抱歉，您没有相关权限去操作"),
    PARAMETER_MISSING(600,"参数缺失"),

    USERNAME_REPEAT(3000100,"用户account已存在"),
    PHONE_REPEAT(3000101,"手机号已存在"),
    EMAIL_REPEAT(3000102,"邮箱已存在"),

    COLLEGEID_REPEAT(4000100,"学院id已存在"),
    COLLEGENAME_REPEAT(4000101,"学院名称已存在"),

    MAJORID_REPEAT(5000100,"专业id已存在"),
    MAJORNAME_REPEAT(4000101,"专业名称已存在");






    private long code;
    private String message;


     private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
