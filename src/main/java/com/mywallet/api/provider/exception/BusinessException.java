package com.mywallet.api.provider.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(final String msg) {
        super(msg);
    }
}
