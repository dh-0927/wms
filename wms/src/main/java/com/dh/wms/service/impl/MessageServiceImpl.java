package com.dh.wms.service.impl;

import com.dh.wms.common.constant.RedisConstant;
import com.dh.wms.common.enums.ReadStatusEnums;
import com.dh.wms.common.enums.UserRoleEnums;
import com.dh.wms.common.exception.BusinessException;
import com.dh.wms.common.exception.BusinessExceptionEnum;
import com.dh.wms.common.resp.PageResp;
import com.dh.wms.common.utils.SnowUtil;
import com.dh.wms.entity.Goods;
import com.dh.wms.service.GoodsService;
import com.dh.wms.entity.Ledger;
import com.dh.wms.entity.Message;
import com.dh.wms.entity.User;
import com.dh.wms.mapper.MessageMapper;
import com.dh.wms.mapper.UserMapper;
import com.dh.wms.req.QueryByDateTimeReq;
import com.dh.wms.service.MessageService;
import com.dh.wms.entity.Store;
import com.dh.wms.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    // 出库时会调用该方法
    // 消息正文格式：张三（18344318413）于 2024-03-18 15:21:22 将 10 数量的手机 出库于 京东商场（店铺位置）
    @Override
    @Transactional
    public void generateMsg(Ledger ledger) {
        // 判断是否是 admin 调用
        if (ledger.getOperator() == UserRoleEnums.ADMIN.getRole()) {
            // 如果是的话不用生成消息，退出方法
            return;
        }
        // 不是 admin 调用则生成消息实体保存进 mysql
        long goodsId = ledger.getGoodsId();
        long operator = ledger.getOperator();
        int quantity = ledger.getQuantity();
        long storeId = ledger.getStoreId();
        Date outboundTime = ledger.getCreateTime();

        Message msg = new Message();
        msg.setId(SnowUtil.getSnowflakeNextId());
        msg.setGoodsId(goodsId);
        msg.setOutboundTime(outboundTime);
        msg.setReadStatus(ReadStatusEnums.UNREAD.getStatus());

        User user = userMapper.queryById(operator);
        Goods goods = goodsService.queryById(goodsId);
        Store store = storeService.queryById(storeId);

        String content = user.getName() + "（" + user.getPhone() + "）于 "
                + outboundTime + " 将 " + quantity + "数量的" + goods.getName()
                + "出库到了" + store.getName() + "（" + store.getLocation() + "）";

        msg.setContent(content);

        messageMapper.save(msg);

        // 再将消息保存进 redis
        stringRedisTemplate.opsForZSet().add(
                RedisConstant.MSG_KEY,
                msg.getId().toString(),
                // 3 个小时延迟
                System.currentTimeMillis() + (3 * 60 * 60 * 1000)
//                System.currentTimeMillis() + (30 * 1000)
        );
    }

    @Override
    public PageResp<Message> queryByDateTime(QueryByDateTimeReq req) {
        int page = req.getPage();
        int size = req.getSize();
        // 开启分页插件
        PageHelper.startPage(page, size);
        // 执行 sql
        List<Message> msgList = messageMapper.queryByDateTime(req.getStart(), req.getEnd());

        PageInfo<Message> pageInfo = new PageInfo<>(msgList);
        // 将结果封装进分页返回结果中
        PageResp<Message> resp = new PageResp<>();
        resp.setCurrent(pageInfo.getPageNum());
        resp.setSize(pageInfo.getPageSize());
        resp.setTotal(pageInfo.getPages());
        resp.setData(msgList);
        return resp;
    }

    @Override
    @Transactional
    public void readMsg(Long id) {
        if (messageMapper.updateStatus(id) != 1) {
            throw new BusinessException(BusinessExceptionEnum.MESSAGE_NOT_EXIST);
        }
        // 已读消息，删除 redis 中的消息
        stringRedisTemplate.opsForZSet().remove(RedisConstant.MSG_KEY, id.toString());
    }


}
