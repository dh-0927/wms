package com.dh.wms.common.exception;

public enum BusinessExceptionEnum {

    OPERATOR_INVALID("操作人无效！"),

    STORE_NOT_EXIST("该门店不存在！"),

    GOODS_NOT_EXIST("该商品不存在！"),

    QUANTITY_INVENTORY("库存不足！"),

    MESSAGE_NOT_EXIST("当前消息不存在！");

    private final String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
