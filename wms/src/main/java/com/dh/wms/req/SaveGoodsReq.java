package com.dh.wms.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class SaveGoodsReq {

    @NotBlank(message = "商品名称不能为空！")
    private String name;

    private String specification;

    @Pattern(regexp = "^\\d{1,10}(\\.\\d{1,2})?$", message = "输入正确的价格！")
    @NotNull(message = "商品价格不能为空！")
    private Double price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
