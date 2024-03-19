package com.dh.wms.common.enums;

public enum ReadStatusEnums {

    UNREAD(0, "未读"),
    READ(1, "已读");
    private final int status;
    private final String desc;

    ReadStatusEnums(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDesc() {
        return this.desc;
    }
}
