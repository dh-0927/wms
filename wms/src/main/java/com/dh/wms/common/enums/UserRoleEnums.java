package com.dh.wms.common.enums;

public enum UserRoleEnums {

    ADMIN(0, "管理员"),
    ORDINARY(1, "普通用户");
    private final int role;
    private final String desc;

    UserRoleEnums(int role, String desc) {
        this.role = role;
        this.desc = desc;
    }

    public int getRole() {
        return this.role;
    }

    public String getDesc() {
        return this.desc;
    }
}
