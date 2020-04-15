package com.haidm.web.authentication.exception;


import org.springframework.security.core.AuthenticationException;

/** 图形验证码 异常类
 * @author shs-xxaqbzymhd
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
