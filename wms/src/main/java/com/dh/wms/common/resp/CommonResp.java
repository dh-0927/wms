package com.dh.wms.common.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonResp<T> {

    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T data;

    public CommonResp(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CommonResp(String message, T content) {
        this.message = message;
        this.data = content;
    }

    public CommonResp(T content) {
        this.data = content;
    }
}

