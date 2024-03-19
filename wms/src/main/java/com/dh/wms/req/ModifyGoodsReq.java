package com.dh.wms.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ModifyGoodsReq {

    @NotNull(message = "商品唯一标识不能为空！")
    private Long id;

    private String name;

    private String specification;

    @Pattern(regexp = "^\\d{1,10}(\\.\\d{1,2})?$", message = "输入正确的价格！")
    private Double price;

}
