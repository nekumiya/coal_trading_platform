package com.guet.coal_trading_platform.exceptions;

/**
 * @author: Hps
 * @date: 2021/3/18 20:27
 * @description:
 */
public class LoginException extends RuntimeException {
    private static final long serialVersionUID=1L;
    private final String message;

    public LoginException(String message) {
        this.message = message;
    }
}
