package com.chenghao.timer;

import com.alibaba.fastjson.JSONObject;
import com.chenghao.domain.Message;
import com.chenghao.mapper.MessageMapper;
import com.chenghao.mq.RabbitmqSender;
import com.chenghao.vo.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenghao
 * @Date: 2019/8/12 0:14
 **/
@Component
@Slf4j
public class ScheduleService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RabbitmqSender rabbitmqSender;

    /**
     * 定时查询message中状态为unConfirm的
     */
    @Scheduled(cron = "0/5 0/1 * * * ?")
    public void scheduleProcess() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("========定时扫描开启，" + simpleDateFormat.format(new Date()));
        List<Message> unConfirm = messageMapper.queryByStatus("unConfirm");
        log.info("========未消费的消息：" + JSONObject.toJSONString(unConfirm));
        if (unConfirm != null && unConfirm.size() > 0) {
            for (Message message : unConfirm) {
                log.info("========往rabbitmq里面发送消息" + JSONObject.toJSONString(message));
                rabbitmqSender.sendMessage(Constant.EXCHANGE_TOPIC_INFORM_ALIPAY,
                        Constant.ROUTINGKEY_INFORM_ALIPAY, message);
            }
        }
    }

}
