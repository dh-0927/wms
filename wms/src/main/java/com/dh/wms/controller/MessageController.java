package com.dh.wms.controller;

import com.dh.wms.common.resp.CommonResp;
import com.dh.wms.common.resp.PageResp;
import com.dh.wms.entity.Message;
import com.dh.wms.req.QueryByDateTimeReq;
import com.dh.wms.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * admin用户能调用消息列表接口获取到商品的相关动态
     * 用户应该能通过选择出库时间的范围来检索相关消息
     * 查询条件：
     * 1. 在出库时间之前（optional，商品的出库时间不能早于该时间）
     * 2. 在出库时间之后（optional，商品的出库时间不能晚于该时间）
     * 3. 当前页
     * 4. 页面数据量
     * 返回：
     * 分页信息
     */
    @GetMapping("/query")
    public CommonResp<PageResp<Message>> queryByDateTime(@RequestBody QueryByDateTimeReq req) {
        PageResp<Message> pageResp = messageService.queryByDateTime(req);
        return new CommonResp<>(pageResp);
    }

    /**
     * 读取消息
     * 用户可以通过读取消息来实现对消息状态的改变，并取消该条消息未读取的短信任务
     * 入参：
     * 1. 消息的身份标识
     * 返回自拟
     */
    @PutMapping("/read/{id}")
    public CommonResp<Object> modifyReadStatus(@PathVariable Long id) {
        messageService.readMsg(id);
        return new CommonResp<>();
    }

}
