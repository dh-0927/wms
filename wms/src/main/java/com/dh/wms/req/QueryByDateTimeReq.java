package com.dh.wms.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class QueryByDateTimeReq {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end;

    @NotNull(message = "查询页不能为空！")
    private Integer page;

    @NotNull(message = "页面数据量不能为空！")
    private Integer size;
}
