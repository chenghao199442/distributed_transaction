package com.chenghao.mq;

import com.alibaba.fastjson.JSONObject;
import com.chenghao.domain.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: chenghao
 * @Date: 2019/8/11 23:57
 **/
@Component
@Slf4j
public class RabbitmqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, Message message) {
        try {
            log.info("========发送消息到MQ，等到余额宝收款：" + JSONObject.toJSONString(message));
            rabbitTemplate.convertAndSend(exchange, routingKey, JSONObject.toJSONString(message));
        } catch (AmqpException e) {
            e.printStackTrace();
        }
    }

}
