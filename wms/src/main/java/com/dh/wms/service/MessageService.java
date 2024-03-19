package com.dh.wms.service;

import com.dh.wms.common.resp.PageResp;
import com.dh.wms.entity.Ledger;
import com.dh.wms.entity.Message;
import com.dh.wms.req.QueryByDateTimeReq;

public interface MessageService {
    /**
     * 当进行出库操作时调用，生成消息保存进 mysql 和 redis
     */
    void generateMsg(Ledger ledger);

    PageResp<Message> queryByDateTime(QueryByDateTimeReq req);

    void readMsg(Long id);

}
