package com.dh.wms.service;

import com.dh.wms.common.resp.PageResp;
import com.dh.wms.entity.Goods;
import com.dh.wms.req.ModifyGoodsReq;
import com.dh.wms.req.QueryByExampleReq;
import com.dh.wms.req.SaveGoodsReq;


public interface GoodsService {
    void save(SaveGoodsReq req);

    void modify(ModifyGoodsReq req);

    void delete(Long id);

    PageResp<Goods> queryByExample(QueryByExampleReq req);

    Goods queryById(long id);
}
