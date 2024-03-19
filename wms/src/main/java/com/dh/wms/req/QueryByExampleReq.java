package com.dh.wms.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryByExampleReq {

    private String name;

    @NotNull(message = "查询页不能为空！")
    private Integer page;

    @NotNull(message = "页面数据量不能为空！")
    private Integer size;
}
