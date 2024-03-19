package com.dh.wms.controller;

import com.dh.wms.common.resp.CommonResp;
import com.dh.wms.common.resp.PageResp;
import com.dh.wms.entity.Ledger;
import com.dh.wms.req.QueryInByExampleReq;
import com.dh.wms.req.SaveLedgerReq;
import com.dh.wms.service.LedgerService;
import com.dh.wms.resp.StatisticGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ledger")
public class LedgerController {

    @Autowired
    private LedgerService ledgerService;

    /**
     * 出库
     * 需要指定商品唯一标识和数量以及所在门店；返回指定门店的商品现存数量。
     * <p>
     * 入库
     * 需要指定商品唯一标识和数量以及所在门店；返回指定门店的商品现存数量。
     * <p>
     * 出入库使用一个接口即可，传入不同的 type
     */
    @PostMapping("/save")
    public CommonResp<Integer> saveLedger(@Validated @RequestBody SaveLedgerReq req) {
        int quantity = ledgerService.saveLedger(req);
        return new CommonResp<>(quantity);
    }

    /**
     * 商品入库流水查询
     * 查询条件包含以下4种:
     * 1. 商品名称
     * 2. 商品入库日期在指定入库日期之后为依据进行查询。
     * 3. 查询页
     * 4. 页面数据量
     * <p>
     * 返回：
     * 分页信息
     */

    @GetMapping("/query")
    public CommonResp<PageResp<Ledger>> query(@Validated @RequestBody QueryInByExampleReq req) {
        PageResp<Ledger> pageResp = ledgerService.queryInByExample(req);
        return new CommonResp<>(pageResp);
    }

    /**
     * 统计特定商品：
     * 查询条件包含以下：
     * 1. 商品的身份标识
     * 返回至少包含以下指定信息：
     * 1. 商品当前库存
     * 2. 现有商品总价
     */

    @GetMapping("/statistic/{goodsId}")
    public CommonResp<StatisticGoodsVo> statistic(@PathVariable("goodsId") Long goodsId) {
        StatisticGoodsVo statistic = ledgerService.statistic(goodsId);
        return new CommonResp<>(statistic);
    }

}
