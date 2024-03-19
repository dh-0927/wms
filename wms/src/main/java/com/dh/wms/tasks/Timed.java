package com.dh.wms.tasks;

import com.dh.wms.common.constant.RedisConstant;
import com.dh.wms.entity.Message;
import com.dh.wms.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

/***
 * 每隔一段时间去 redis 中查询已经超过三个小时的消息，发送给 admin
 */
@Component
public class Timed {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MessageMapper messageMapper;

    @Scheduled(cron = "0/5 * * * * ?")
    public void execute() {
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringRedisTemplate.opsForZSet().rangeByScoreWithScores(
                RedisConstant.MSG_KEY,
                0,
                System.currentTimeMillis()
        );
        // 没有到期消息直接退出
        if (typedTuples == null) {
            return;
        }
        typedTuples.stream()
                .map(ZSetOperations.TypedTuple<String>::getValue)
                .filter(Objects::nonNull)
                .forEach((msgId) -> {
                    // 拿到了消息 id，从数据库中查找出消息正文
                    Message msg = messageMapper.getById(Long.parseLong(msgId));
                    String content = msg.getContent();
                    // TODO：拿到了消息正文，给 admin 发短信
                    System.out.println("给 admin 发送消息：" + content);
                    // 发完消息后删除 redis 中的消息
                    stringRedisTemplate.opsForZSet().remove(RedisConstant.MSG_KEY, msgId);
                });

    }
}
