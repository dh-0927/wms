package com.dh.wms.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class QueryInByExampleReq {

    @NotBlank(message = "商品名称不能为空！")
    private String name;

    @NotNull(message = "必须指定日期！")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date specifyTime;

    @NotNull(message = "查询页不能为空！")
    private Integer page;

    @NotNull(message = "页面数据量不能为空！")
    private Integer size;
}
