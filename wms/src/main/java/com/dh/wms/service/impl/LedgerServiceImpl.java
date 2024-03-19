package com.dh.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.dh.wms.common.enums.LedgerTypeEnums;
import com.dh.wms.common.exception.BusinessException;
import com.dh.wms.common.exception.BusinessExceptionEnum;
import com.dh.wms.common.resp.PageResp;
import com.dh.wms.common.utils.SnowUtil;
import com.dh.wms.entity.Goods;
import com.dh.wms.service.GoodsService;
import com.dh.wms.entity.Ledger;
import com.dh.wms.mapper.LedgerMapper;
import com.dh.wms.req.QueryInByExampleReq;
import com.dh.wms.req.SaveLedgerReq;
import com.dh.wms.service.LedgerService;
import com.dh.wms.resp.StatisticGoodsVo;
import com.dh.wms.service.MessageService;
import com.dh.wms.service.UserService;
import com.dh.wms.entity.Store;
import com.dh.wms.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private LedgerMapper ledgerMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public int saveLedger(SaveLedgerReq req) {

        Long goodsId = req.getGoodsId();
        Long storeId = req.getStoreId();
        int quantity = req.getQuantity();
        int type = Integer.parseInt(req.getType());
        Long operator = req.getOperator();

        // 先判断操作人是否有效
        Integer userRole = userService.queryRoleById(operator);
        if (userRole == null) {
            throw new BusinessException(BusinessExceptionEnum.OPERATOR_INVALID);
        }

        // 再判断商品 id 和门店 id 是否有效
        Store store = storeService.queryById(storeId);
        Goods goods = goodsService.queryById(goodsId);
        if (store == null) {
            throw new BusinessException(BusinessExceptionEnum.STORE_NOT_EXIST);
        }
        if (goods == null) {
            throw new BusinessException(BusinessExceptionEnum.GOODS_NOT_EXIST);
        }

        // 为台账实体设置属性
        Ledger ledger = BeanUtil.copyProperties(req, Ledger.class);
        ledger.setId(SnowUtil.getSnowflakeNextId());
        DateTime time = DateTime.now();
        ledger.setCreateTime(time);
        ledger.setUpdateTime(time);

        // 之前的存量
        int startQuantity = getQuantity(storeId, goodsId);

        // 判断是入库操作还是出库操作
        if (LedgerTypeEnums.inbound.getType() == type) {
            // 如果是入库直接插入
            ledgerMapper.save(ledger);
            return startQuantity + quantity;
        }
        // 如果是出库操作还需判断该门店的该商品库存是否足够
        if (startQuantity < quantity) {
            // 不够抛异常
            throw new BusinessException(BusinessExceptionEnum.QUANTITY_INVENTORY);
        }
        // 足够的话直接插入
        ledgerMapper.save(ledger);
        // 再生成消息
        messageService.generateMsg(ledger);

        return startQuantity - quantity;
    }

    @Override
    public PageResp<Ledger> queryInByExample(QueryInByExampleReq req) {
        String name = req.getName();
        Date time = req.getSpecifyTime();
        int page = req.getPage();
        int size = req.getSize();
        // 开启分页
        PageHelper.startPage(page, size);
        // 执行查询sql
        List<Ledger> ledgers = ledgerMapper.queryByExample(name, time, LedgerTypeEnums.inbound.getType());
        PageInfo<Ledger> pageInfo = new PageInfo<>(ledgers);
        // 封装信息返回
        PageResp<Ledger> resp = new PageResp<>();
        resp.setCurrent(pageInfo.getPageNum());
        resp.setSize(pageInfo.getPageSize());
        resp.setTotal(pageInfo.getPages());
        resp.setData(pageInfo.getList());
        return resp;
    }

    @Override
    public StatisticGoodsVo statistic(Long goodsId) {
        // 首先判断商品是否存在
        Goods goods = goodsService.queryById(goodsId);
        if (goods == null) {
            throw new BusinessException(BusinessExceptionEnum.GOODS_NOT_EXIST);
        }
        // 查询库存
        int quantity = getQuantity(null, goodsId);
        double totalPrice = quantity * goods.getPrice();
        return new StatisticGoodsVo(quantity, totalPrice);
    }

    /**
     * 计算某个门店的某个商品的存量
     * 内部调用
     */

    private int getQuantity(Long storeId, Long goodsId) {
        int inQuantity = ledgerMapper.queryByGoodsIdAndStoreIdAndType(goodsId, storeId, LedgerTypeEnums.inbound.getType());
        int outQuantity = ledgerMapper.queryByGoodsIdAndStoreIdAndType(goodsId, storeId, LedgerTypeEnums.outbound.getType());
        return inQuantity - outQuantity;
    }
}
