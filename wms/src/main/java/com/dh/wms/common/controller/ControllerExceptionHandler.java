package com.dh.wms.common.controller;

import com.dh.wms.common.exception.BusinessException;
import com.dh.wms.common.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResp<String> otherExceptionHandler(Exception e) {
        log.error("系统异常：{}", e.getMessage());
        return new CommonResp<>(false, "系统出现异常，请联系管理员");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResp<String> businessExceptionHandler(BusinessException e) {
        log.error("业务异常：{}", e.getExceptionEnum().getDesc());
        return new CommonResp<>(false, e.getExceptionEnum().getDesc());
    }

    /**
     * 参数格式转换异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CommonResp<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("：{}", e.getMessage());
        return new CommonResp<>(false, "请正确填写参数！");
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public CommonResp<String> bindExceptionHandler(BindException e) {

        BindingResult result = e.getBindingResult();
        String errorInfo = null;
        if (result.hasErrors()) {
            errorInfo = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .reduce((m1, m2) -> m1 + ", " + m2)
                    .orElse("参数校验有误！");
        }
        log.error("参数校验异常：{}", errorInfo);
        return new CommonResp<>(false, errorInfo);
    }
}