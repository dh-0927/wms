package com.dh.wms.common.enums;

public enum LedgerTypeEnums {

    outbound(0, "出库"),

    inbound(1, "入库");

    private final int type;
    private final String desc;

    LedgerTypeEnums(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }


}
