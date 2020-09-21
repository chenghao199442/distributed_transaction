package com.chenghao.mq;

import com.chenghao.service.AlipayService;
import com.chenghao.vo.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: chenghao
 * @Date: 2019/8/12 20:30
 **/
@Component
@Slf4j
public class MessageListener {

    @Autowired
    private AlipayService alipayService;

    @RabbitListener(queues = Constant.QUEUE_INFORM_YUEBAO)
    public void process(String param) {
        log.info("==========余额宝已经处理完成，回调支付宝进行状态更新" + param);
        alipayService.updateMessage(param);
    }

}
