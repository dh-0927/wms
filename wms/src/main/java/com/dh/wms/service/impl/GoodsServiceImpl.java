package com.dh.wms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.dh.wms.common.resp.PageResp;
import com.dh.wms.common.utils.SnowUtil;
import com.dh.wms.entity.Goods;
import com.dh.wms.mapper.GoodsMapper;
import com.dh.wms.req.ModifyGoodsReq;
import com.dh.wms.req.QueryByExampleReq;
import com.dh.wms.req.SaveGoodsReq;
import com.dh.wms.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public void save(SaveGoodsReq req) {
        Goods goods = BeanUtil.copyProperties(req, Goods.class);
        goods.setId(SnowUtil.getSnowflakeNextId());
        DateTime time = DateTime.now();
        goods.setCreateTime(time);
        goods.setUpdateTime(time);
        goodsMapper.insert(goods);
    }

    @Override
    public void modify(ModifyGoodsReq req) {
        Goods goods = BeanUtil.copyProperties(req, Goods.class);
        goods.setUpdateTime(DateTime.now());
        goodsMapper.updateByPrimaryKey(goods);
    }

    @Override
    public void delete(Long id) {
        goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResp<Goods> queryByExample(QueryByExampleReq req) {
        int page = req.getPage();
        int size = req.getSize();
        String name = req.getName();
        // 开启分页插件
        PageHelper.startPage(page, size);
        // 执行 sql
        List<Goods> goodsList = goodsMapper.QueryByName(name);

        PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
        // 将结果封装进分页返回结果中
        PageResp<Goods> resp = new PageResp<>();
        resp.setCurrent(pageInfo.getPageNum());
        resp.setSize(pageInfo.getPageSize());
        resp.setTotal(pageInfo.getPages());
        resp.setData(goodsList);
        return resp;
    }

    @Override
    public Goods queryById(long id) {
        return goodsMapper.queryById(id);
    }


}
