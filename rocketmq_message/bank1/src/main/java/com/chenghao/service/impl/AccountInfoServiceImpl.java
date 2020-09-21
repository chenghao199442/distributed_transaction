package com.chenghao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chenghao.domain.AccountChangeEvent;
import com.chenghao.domain.AccountInfo;
import com.chenghao.mapper.AccountInfoMapper;
import com.chenghao.mapper.MessageLogMapper;
import com.chenghao.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @auther Cheng Hao
 * @date 2020/9/15 21:17
 */
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private MessageLogMapper messageLogMapper;

    /**
     * 发送扣减库存的消息
     *
     * @param accountChangeEvent
     */
    @Override
    public void sendUpdateAccountBalance(AccountChangeEvent accountChangeEvent) {
        //构建消息内容
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accountChange", accountChangeEvent);
        String jsonString = jsonObject.toJSONString();
        Message<String> message = MessageBuilder.withPayload(jsonString).build();
        //发送一条事务消息
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("producer_group_bank1", "topic_bank", message, null);
        log.info("send transaction message body = {},result={}", message.getPayload(), result.getSendStatus());
    }

    /**
     * 扣减库存
     *
     * @param accountChangeEvent
     */
    @Transactional
    @Override
    public void updateAccountInfoBalance(AccountChangeEvent accountChangeEvent) {
        //判断是否已经转账过，幂等判断
        if (messageLogMapper.isExistTx(accountChangeEvent.getTxNo()) > 0) {
            return;
        }
        //判断余额是否充足
        AccountInfo accountInfo = accountInfoMapper.getAccountInfo(accountChangeEvent.getProducerAccountNo());
        if (accountInfo.getAccountBalance() < accountChangeEvent.getAccountBalance()) {
            log.error("余额不足，转账失败");
            return;
        }
        //修改余额
        accountInfoMapper.updateAccountBalance(accountChangeEvent.getProducerAccountNo(), accountChangeEvent.getAccountBalance());
        //添加一条消息
        messageLogMapper.addTx(accountChangeEvent.getTxNo());
    }
}
