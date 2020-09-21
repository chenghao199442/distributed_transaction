package com.chenghao.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenghao.domain.AccountChangeEvent;
import com.chenghao.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 22:02
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = "topic_bank", consumerGroup = "consumer_group_bank2")
public class ConsumerMsgListener implements RocketMQListener<String> {

    @Autowired
    private AccountInfoService accountInfoService;

    /**
     * 监听消息
     * @param message
     */
    @Override
    public void onMessage(String message) {
        log.info("消费消息:{}", message);
        //接受到消息后，解析出消息内容
        JSONObject jsonObject = JSONObject.parseObject(message);
        String accountChange = jsonObject.getString("accountChange");
        AccountChangeEvent accountChangeEvent = JSONObject.parseObject(accountChange, AccountChangeEvent.class);
        //执行本地事务
        accountInfoService.addAccountInfoBalance(accountChangeEvent);
    }
}
