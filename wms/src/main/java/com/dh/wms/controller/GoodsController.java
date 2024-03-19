package com.dh.wms.controller;

import com.dh.wms.common.resp.CommonResp;
import com.dh.wms.common.resp.PageResp;
import com.dh.wms.entity.Goods;
import com.dh.wms.req.ModifyGoodsReq;
import com.dh.wms.req.QueryByExampleReq;
import com.dh.wms.req.SaveGoodsReq;
import com.dh.wms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品
     * （以便对商品的库存进行管理）。对商品的操作会在台账模块要求的接口完成。
     * 入参自拟
     * 返回自拟
     */
    @PostMapping("/save")
    public CommonResp<Object> save(@Validated @RequestBody SaveGoodsReq req) {
        goodsService.save(req);
        return new CommonResp<>();
    }

    /**
     * 修改商品
     * 入参：
     * 身份标识以及能够被修改的商品属性...
     * 返回自拟
     */
    @PutMapping("/modify")
    public CommonResp<Object> modify(@Validated @RequestBody ModifyGoodsReq req) {
        goodsService.modify(req);
        return new CommonResp<>();
    }

    /**
     * 删除商品
     * 入参：
     * 身份标识（以便确认操作的商品）。
     * 返回：
     * 是否成功
     */
    @DeleteMapping("/delete/{id}")
    public CommonResp<Object> delete(@PathVariable Long id) {
        goodsService.delete(id);
        return new CommonResp<>();
    }

    /**
     * 查询商品
     * （按商品录入时间降序）
     * 入参：
     * 1. 商品名称(optional)
     * 2. 查询页
     * 3. 页面数据量
     * 返回：
     * 分页信息
     */
    @GetMapping("/query")
    public CommonResp<PageResp<Goods>> query(@Validated @RequestBody QueryByExampleReq req) {
        PageResp<Goods> goodsList = goodsService.queryByExample(req);
        return new CommonResp<>(goodsList);
    }

}
