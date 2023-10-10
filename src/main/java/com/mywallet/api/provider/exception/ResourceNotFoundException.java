package com.mywallet.api.provider.exception;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String msg) {
        super(msg);
    }
}
