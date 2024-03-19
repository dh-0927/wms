package com.dh.wms.common.exception;

public class BusinessException extends RuntimeException{

    private final BusinessExceptionEnum exceptionEnum;

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public BusinessExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
