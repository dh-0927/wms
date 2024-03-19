package com.dh.wms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private Long id;

    private Long goodsId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outboundTime;

    private String content;

    private int readStatus;
}
