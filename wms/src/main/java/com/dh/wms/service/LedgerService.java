package com.dh.wms.service;


import com.dh.wms.common.resp.PageResp;
import com.dh.wms.entity.Ledger;
import com.dh.wms.req.QueryInByExampleReq;
import com.dh.wms.req.SaveLedgerReq;
import com.dh.wms.resp.StatisticGoodsVo;

public interface LedgerService {
    int saveLedger(SaveLedgerReq req);

    PageResp<Ledger> queryInByExample(QueryInByExampleReq req);

    StatisticGoodsVo statistic(Long goodsId);
}
