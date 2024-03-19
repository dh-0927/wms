package com.dh.wms.mapper;

import com.dh.wms.entity.Ledger;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface LedgerMapper {

    int save(Ledger ledger);

    /**
     * 根据 商品Id 和 门店Id 查询入库或者出库数量
     */
    int queryByGoodsIdAndStoreIdAndType(Long goodsId, Long storeId, int type);

    /**
     * 查询在指定日期之后的出入库流水
     */
    List<Ledger> queryByExample(String name, Date time, int type);
}
