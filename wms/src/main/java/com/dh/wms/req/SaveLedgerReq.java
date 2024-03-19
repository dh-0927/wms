package com.dh.wms.req;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SaveLedgerReq {

    @NotNull(message = "商品唯一标识不能为空！")
    private Long goodsId;

    @NotNull(message = "操作人不能为空！")
    private Long operator;

    @NotNull(message = "必须输入出入库标识（0 / 1）")
    @Pattern(regexp = "^([0|1])$", message = "错误的出入库标识 (0 / 1)！")
    private String type;

    @NotNull(message = "商品数量不能为空！")
    @Pattern(regexp = "^[1-9]\\d*$", message = "数量必须为正整数")
    private Integer quantity;

    @NotNull(message = "门店唯一标识不能为空！")
    private Long storeId;
}
