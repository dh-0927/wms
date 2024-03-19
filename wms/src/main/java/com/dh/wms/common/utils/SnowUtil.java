package com.dh.wms.common.utils;

import cn.hutool.core.util.IdUtil;

/**
 * 使用雪花算法生成 Id
 * 工具类
 */
public class SnowUtil {
    private static final long dataCenterId = 1;  //数据中心
    private static final long workerId = 1;     //机器标识

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextId();
    }

}
