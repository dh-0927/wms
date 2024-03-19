package com.dh.wms.common.resp;

import lombok.Data;

import java.util.List;

@Data
public class PageResp<T> {

    private int current;
    private int size;
    private int total;
    private List<T> data;
}
