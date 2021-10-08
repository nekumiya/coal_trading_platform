package com.guet.coal_trading_platform.exceptions;

/**
 * 自定义业务异常
 * Created by 欲隐君。 on 2021/3/27
 */
public class MyException extends RuntimeException{
    private long code;

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, long code) {
        super(message);
        this.code = code;
    }

    public long getCode() {
        return this.code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MyException(code=" + this.getCode() + ")";
    }

}
